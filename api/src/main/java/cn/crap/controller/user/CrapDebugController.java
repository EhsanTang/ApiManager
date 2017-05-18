package cn.crap.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.crap.dto.DebugInterfaceParamDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.dao.IModuleDao;
import cn.crap.inter.dao.IProjectDao;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.ICommentService;
import cn.crap.inter.service.table.IDebugService;
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.inter.service.tool.ISearchService;
import cn.crap.model.Article;
import cn.crap.model.Debug;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/user/crapDebug")
public class CrapDebugController extends BaseController<Article>{
	@Autowired
	private IArticleService articleService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private ISearchService luceneService;
	@Autowired
	private ICommentService commentService;
	@Autowired
	private IDebugService debugService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private IProjectDao projectDao;
	@Autowired
	private IModuleDao moduleDao;

	@RequestMapping("/synch.do")
	@ResponseBody
	@AuthPassport
	public JsonResult synch(@RequestBody String body) throws MyException{
		List<DebugInterfaceParamDto> list = JSON.parseArray(body, DebugInterfaceParamDto.class);
		LoginInfoDto user = Tools.getUser();
		String projectId = ( user.getId().length() > 10? user.getId().substring(0, user.getId().length() - 6) : user.getId() ) + "-debug";
		Project project = projectDao.get(projectId);
		if( project == null){
			project = new Project();
			project.setId(projectId);
			project.setCover("/resources/images/cover.png");
			project.setLuceneSearch(Byte.valueOf("0"));
			project.setName("默认调试项目");
			project.setStatus(Byte.valueOf("-1"));
			project.setSequence(0);
			project.setType(1);
			project.setUserId(user.getId());
			project.setCreateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm_ss));
			project.setRemark("");
			projectService.save(project);
		}
		for(DebugInterfaceParamDto d: list){
			if(d==null || MyString.isEmpty(d.getModuleId())){
				continue;
			}
			Module module = moduleDao.get(d.getModuleId());
			if( module == null && d.getStatus()!=-1){
				try{
					module = new Module();
					module.setId(d.getModuleId());
					module.setName(d.getModuleName());
					module.setCreateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm_ss));
					module.setSequence(0);
					module.setProjectId(project.getId());
					module.setUserId(user.getId());
					module.setRemark("");
					module.setUrl("");
					module.setVersion(d.getVersion()==null?0:d.getVersion());
					moduleService.save(module);
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
			}else{
				try{
					if(d.getStatus() != null && d.getStatus() == -1){
						Module delete = new Module();
						delete.setId(d.getModuleId());
						moduleService.delete(delete);
						debugService.update("delete from Debug where moduleId=:moduleId", Tools.getMap("moduleId", d.getModuleId()));
					}
					else if(d.getVersion() == null || module.getVersion() < d.getVersion()){
						module.setVersion(d.getVersion()==null?0:d.getVersion());
						module.setName(d.getModuleName());
						moduleService.update(module);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			// 先删除，在同步
			for(Debug debug : d.getDebugs()){
				try{
					if(MyString.isEmpty( debug.getId())){
						continue;
					}
					if (debug.getStatus() == -1){
						debugService.delete(debug);
					}
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
			}
			int totalNum = debugService.getCount(Tools.getMap("uid", user.getId()));
			if (totalNum > 100){
				return new JsonResult("000058");
			}
			
			for(Debug debug : d.getDebugs()){
				try{
					if(MyString.isEmpty( debug.getId())){
						continue;
					}
					if (debug.getStatus() == -1){
						continue;
					}
					debug.setUid(user.getId());
					debug.setCreateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm_ss));
					debug.setSequence(0);
					debugService.save(debug);
				}catch(Exception e){
					Debug old = debugService.get(debug.getId());
					if(old.getVersion() < debug.getVersion()){
						debug.setCreateTime(old.getCreateTime());
						debug.setModuleId(old.getModuleId());
						debug.setSequence(old.getSequence());
						debug.setStatus(old.getStatus());
						debug.setUid(user.getId());
						debugService.update(debug);
					}
					continue;
				}
				totalNum = totalNum + 1;
			}
		}
		
		List<Module> modules = moduleService.findByMap(Tools.getMap("projectId",projectId), null, "createTime desc");
		List<String> moduleIds = new ArrayList<String>();
		for (Module m:modules){
			moduleIds.add(m.getId());
		}
		List<Debug> debugs = debugService.findByMap(Tools.getMap("moduleId|in", moduleIds), null, "createTime desc");
		Map<String,List<Debug>> mapDebugs = new HashMap<String,List<Debug>>();
		for(Debug d:debugs){
			List<Debug> moduleDebugs = mapDebugs.get(d.getModuleId());
			if(moduleDebugs == null){
				moduleDebugs = new ArrayList<Debug>();
				mapDebugs.put(d.getModuleId(), moduleDebugs);
			}
			moduleDebugs.add(d);
		}
		
		List<DebugInterfaceParamDto> returnlist = new ArrayList<DebugInterfaceParamDto>();		
		for (Module m:modules){
			DebugInterfaceParamDto debugDto = new DebugInterfaceParamDto();
			debugDto.setModuleId(m.getId());
			debugDto.setModuleName(m.getName());
			debugDto.setVersion(m.getVersion());
			debugDto.setStatus(m.getStatus());
			debugDto.setDebugs(mapDebugs.get(m.getId()) == null? new ArrayList<Debug>(): mapDebugs.get(m.getId()));
			returnlist.add(debugDto);
		}
		
		return new JsonResult(1,returnlist);
	}
}
