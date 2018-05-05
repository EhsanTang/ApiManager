package cn.crap.controller.user;

import java.util.*;

import cn.crap.adapter.DebugAdapter;
import cn.crap.enumer.MyError;
import cn.crap.model.mybatis.*;
import cn.crap.service.custom.CustomDebugService;
import cn.crap.service.custom.CustomModuleService;
import cn.crap.service.mybatis.DebugService;
import cn.crap.service.mybatis.ModuleService;
import cn.crap.service.mybatis.ProjectService;
import cn.crap.utils.LoginUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.crap.dto.DebugDto;
import cn.crap.dto.DebugInterfaceParamDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.utils.MD5;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/user/crapDebug")
public class CrapDebugController extends BaseController{
	@Autowired
	private DebugService debugService;
	@Autowired
	private CustomDebugService customDebugService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private CustomModuleService customModuleService;

	@RequestMapping("/synch.do")
	@ResponseBody
	@AuthPassport
	public JsonResult synch(@RequestBody String body) throws MyException{
		List<DebugInterfaceParamDto> list = JSON.parseArray(body, DebugInterfaceParamDto.class);
		LoginInfoDto user = LoginUserHelper.getUser();
		
		// 调试项目ID唯一，根据用户ID生成，不在CrapApi网站显示
		String projectId = MD5.encrytMD5(user.getId(), "").substring(0, 20) + "-debug";
		Project project = projectService.getById(projectId);
		if( project == null){
			project = new Project();
			project.setId(projectId);
			project.setCover("/resources/images/cover.png");
			project.setLuceneSearch(Byte.valueOf("0"));
			project.setName("默认调试项目");
			project.setStatus(Byte.valueOf("-1"));
			project.setSequence(0);
			project.setType(Byte.valueOf("1"));
			project.setUserId(user.getId());
			project.setCreateTime(new Date());
			project.setRemark("");

			projectService.insert(project);
		}
		int moduleSequence = 0;
		for(DebugInterfaceParamDto d: list){
			if(d==null || MyString.isEmpty(d.getModuleId())){
				continue;
			}
			d.setModuleId( Tools.handleId(user,d.getModuleId()) );
			Module module = moduleService.getById(d.getModuleId());
			if( module == null && d.getStatus()!=-1){
				try{
					module = new Module();
					module.setId(d.getModuleId());
					module.setName(d.getModuleName());
					module.setCreateTime(new Date());
					module.setSequence(moduleSequence);
					module.setProjectId(project.getId());
					module.setUserId(user.getId());
					module.setRemark("");
					module.setUrl("");
					module.setVersion(d.getVersion()==null?0:d.getVersion());
					moduleService.insert(module);
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
			}else{
				try{
					if(d.getStatus() != null && d.getStatus() == -1){
						Module delete = new Module();
						delete.setId(d.getModuleId());
						moduleService.delete(delete.getId());
						customDebugService.deleteByModelId(d.getModuleId());
					}
					else if(d.getVersion() == null || module.getVersion() <= d.getVersion()){
						module.setVersion(d.getVersion()==null?0:d.getVersion());
						module.setName(d.getModuleName());
						module.setSequence(moduleSequence);
						moduleService.update(module);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			moduleSequence = moduleSequence + 1;
			// 先删除，在同步
			for(DebugDto debug : d.getDebugs()){
				debug.setId(Tools.handleId(user, debug.getId()));
				debug.setModuleId(Tools.handleId(user, debug.getModuleId()));
				try{
					if(MyString.isEmpty( debug.getId())){
						continue;
					}
					Debug old = debugService.getById(debug.getId());
					if (debug.getStatus() == -1 && old != null && old.getModuleId().equals(debug.getModuleId())){
						debugService.delete(debug.getId());
					}
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
			}

			DebugCriteria example = new DebugCriteria();
			example.createCriteria().andUidEqualTo(user.getId());
			int totalNum = debugService.countByExample(example);
			if (totalNum > 100){
				return new JsonResult(MyError.E000058);
			}
			int debugSequence = 0;
			for(DebugDto debug : d.getDebugs()){
				debug.setSequence(debugSequence);
				try{
					if(MyString.isEmpty( debug.getId())){
						continue;
					}
					if (debug.getStatus() == -1){
						continue;
					}
					debug.setUid(user.getId());
					debug.setCreateTime(new Date());
					debugService.insert(DebugAdapter.getModel(debug));
					totalNum = totalNum + 1;
				}catch(Exception e){
					Debug old = debugService.getById(debug.getId());
					if(old.getVersion() <= debug.getVersion()){
						debug.setCreateTime(old.getCreateTime());
						if(old.getModuleId().equals(debug.getModuleId())){
							debug.setStatus(old.getStatus());
							debug.setUid(user.getId());
							debugService.update(DebugAdapter.getModel(debug));
						}	
					}
				}
				debugSequence = debugSequence + 1;
			}
		}
		
		List<Module> modules = customModuleService.queryByProjectId(projectId);
		List<String> moduleIds = new ArrayList<String>();
		for (Module m:modules){
			moduleIds.add(m.getId());
		}
		DebugCriteria example = new DebugCriteria();
		example.createCriteria().andModuleIdIn(moduleIds);
		example.setOrderByClause("sequence asc");
		List<Debug> debugs = debugService.selectByExample(example);
		Map<String,List<DebugDto>> mapDebugs = new HashMap<>();
		for(Debug d:debugs){
			List<DebugDto> moduleDebugs = mapDebugs.get(d.getModuleId());
			if(moduleDebugs == null){
				moduleDebugs = new ArrayList<>();
				mapDebugs.put(d.getModuleId(), moduleDebugs);
			}
			moduleDebugs.add(DebugAdapter.getDto(d));
		}
		
		List<DebugInterfaceParamDto> returnlist = new ArrayList<DebugInterfaceParamDto>();		
		for (Module m:modules){
			DebugInterfaceParamDto debugDto = new DebugInterfaceParamDto();
			debugDto.setModuleId(Tools.unhandleId(m.getId()));
			debugDto.setModuleName(m.getName());
			debugDto.setVersion(m.getVersion());
			debugDto.setStatus(m.getStatus());
			debugDto.setDebugs(mapDebugs.get(m.getId()) == null? new ArrayList<DebugDto>(): mapDebugs.get(m.getId()));
			returnlist.add(debugDto);
		}
		return new JsonResult(1,returnlist);
	}
	
}
