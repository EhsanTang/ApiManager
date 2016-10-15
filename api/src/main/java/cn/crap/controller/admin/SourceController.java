package cn.crap.controller.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.SearchDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.table.ISourceService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.inter.service.tool.ISearchService;
import cn.crap.model.Source;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/back/source")
public class SourceController extends BaseController<Source>{

	@Autowired
	private ISourceService sourceService;
	@Autowired
	private IModuleService dataCenterService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private ISearchService luceneService;
	/**
	 * 
	 * @param source
	 * @param currentPage 当前页
	 * @param pageSize 每页显示多少条，-1表示查询全部
	 * @return
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Source source,@RequestParam(defaultValue="1") int currentPage){
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		// 搜索条件
		Map<String,Object> map = Tools.getMap("name|like", source.getName(), "moduleId", source.getModuleId());
		//returnMap.put("sources", sourceService.findByMap(map, " new Source(id,createTime,status,sequence,name,filePath,directoryId,updateTime) ", page, null));
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("sources", sourceService.findByMap(map, page, null));

		map.clear();
		//map = Tools.getMap("projectId", source.getm, "type", "DIRECTORY");
		//returnMap.put("directorys",  dataCenterService.findByMap(map, null, null));
		return new JsonResult(1, returnMap, page);
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Source source){
		Source model;
		if(!MyString.isEmpty(source.getId())){
			model = sourceService.get(source.getId());
		}else{
			model=new Source();
			model.setModuleId(source.getModuleId());
		}
		return new JsonResult(1,model);
		
	}
	@RequestMapping("/webDetail.do")
	@ResponseBody
	public JsonResult webDetail(@ModelAttribute Source source,String password,String visitCode) throws MyException{
		Source model;
		if(!MyString.isEmpty(source.getId())){
			model = sourceService.get(source.getId());
		}else{
			throw new MyException("000020");
		}
		Tools.canVisitModule(cacheService.getModule(model.getModuleId()).getPassword(), password, visitCode, request);
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/webList.do")
	@ResponseBody
	public JsonResult webList(@ModelAttribute Source source,@RequestParam(defaultValue="1") int currentPage,String password,String visitCode,
			@RequestParam(defaultValue="无") String directoryName) throws MyException{
		Tools.canVisitModule(cacheService.getModule(source.getModuleId()).getPassword(), password, visitCode, request);
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		// 搜索条件
		Map<String,Object> map = Tools.getMap("name|like", source.getName(), "directoryId", source.getModuleId());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("sources", sourceService.findByMap(map, " new Source(id,createTime,status,sequence,name,filePath,directoryId,updateTime) ", page, null));

		map.clear();
		map = Tools.getMap("parentId", source.getModuleId(), "type", "DIRECTORY");
		returnMap.put("directorys",  dataCenterService.findByMap(map, null, null));
		
		return new JsonResult(1, returnMap, page, 
				Tools.getMap("crumbs", Tools.getCrumbs("目录列表:"+directoryName,"void")));
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute Source source) throws Exception{
		
			// TODO 权限
			if(MyString.isEmpty(source.getFilePath())){
				throw new MyException("000016");
			}
			
			// 判断版本号是否正确 .CAV.文件标识.版本号 
			if( !source.getFilePath().contains(".CAV.") ){
				throw new MyException("000017");
			}
			
			// 校验文件名是否正确
			String vsesions[];
			try{
				vsesions = source.getFilePath().split("\\.");
				if( !vsesions[vsesions.length-4].equals("CAV") ){
					throw new MyException("000017");
				}
	  			Long.parseLong(vsesions[vsesions.length-2]);
			}catch(Exception e){
				throw new MyException("000017");
			}
			if(!MyString.isEmpty(source.getId())){
				Source oldSource = sourceService.get(source.getId());
				String oldVsesions[]  = oldSource.getFilePath().split("\\.");
				if( !oldVsesions[oldVsesions.length-3].equals(vsesions[vsesions.length-3])){
					// 文件标识有误
					throw new MyException("000018");
				}
					
				// 新版本号必须 >= 旧版本号
				if( (Long.parseLong(oldVsesions[oldVsesions.length-2])) > Long.parseLong(vsesions[vsesions.length-2]) ){
						// 文件标识有误
					throw new MyException("000019");
				}else if((Long.parseLong(oldVsesions[oldVsesions.length-2])) == Long.parseLong(vsesions[vsesions.length-2]) ){
					// 新旧版本号一致，不更新文档地址
					source.setFilePath(oldSource.getFilePath());
				}
			}
			
			SearchDto searchDto = source.toSearchDto(null);
			source.setUpdateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm_ss));
			if(!MyString.isEmpty(source.getId())){
				sourceService.update(source);
			}else{
				source.setId(null);
				sourceService.save(source);
			}
			
			
			luceneService.update(searchDto);
			return new JsonResult(1,source);
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport
	public JsonResult delete(@ModelAttribute Source source) throws MyException, IOException{
		// TODO 权限
		sourceService.delete(source);
		luceneService.delete(new SearchDto(source.getId()));
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) {
		Source change = sourceService.get(changeId);
		Source model = sourceService.get(id);
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		sourceService.update(model);
		sourceService.update(change);
		return new JsonResult(1, null);
	}

}
