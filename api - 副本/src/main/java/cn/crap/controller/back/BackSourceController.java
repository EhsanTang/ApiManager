package cn.crap.controller.back;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.ISourceService;
import cn.crap.model.Source;
import cn.crap.utils.Const;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.GetBeanBySetting;
import cn.crap.utils.GetTextFromFile;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/back/source")
public class BackSourceController extends BaseController<Source>{

	@Autowired
	private ISourceService sourceService;
	@Autowired
	private IDataCenterService dataCenterService;
	@Autowired
	private ICacheService cacheService;
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
		page.setCurrentPage(currentPage);
		// 搜索条件
		map = Tools.getMap("name|like", source.getName(), "directoryId", source.getDirectoryId());
		//returnMap.put("sources", sourceService.findByMap(map, " new Source(id,createTime,status,sequence,name,filePath,directoryId,updateTime) ", page, null));
		returnMap.put("sources", sourceService.findByMap(map, page, null));

		map.clear();
		map = Tools.getMap("parentId", source.getDirectoryId(), "type", "DIRECTORY");
		returnMap.put("directorys",  dataCenterService.findByMap(map, null, null));
		return new JsonResult(1, returnMap, page);
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Source source){
		if(!MyString.isEmpty(source.getId())){
			model = sourceService.get(source.getId());
		}else{
			model=new Source();
			model.setDirectoryId(source.getDirectoryId());
		}
		return new JsonResult(1,model);
		
	}
	@RequestMapping("/webDetail.do")
	@ResponseBody
	public JsonResult webDetail(@ModelAttribute Source source,String password,String visitCode) throws MyException{
		if(!MyString.isEmpty(source.getId())){
			model = sourceService.get(source.getId());
		}else{
			throw new MyException("000020");
		}
		Tools.canVisitModule(cacheService.getModule(model.getDirectoryId()).getPassword(), password, visitCode, request);
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/webList.do")
	@ResponseBody
	public JsonResult webList(@ModelAttribute Source source,@RequestParam(defaultValue="1") int currentPage,String password,String visitCode,
			@RequestParam(defaultValue="无") String directoryName) throws MyException{
		Tools.canVisitModule(cacheService.getModule(source.getDirectoryId()).getPassword(), password, visitCode, request);
		
		page.setCurrentPage(currentPage);
		// 搜索条件
		map = Tools.getMap("name|like", source.getName(), "directoryId", source.getDirectoryId());
		returnMap.put("sources", sourceService.findByMap(map, " new Source(id,createTime,status,sequence,name,filePath,directoryId,updateTime) ", page, null));

		map.clear();
		map = Tools.getMap("parentId", source.getDirectoryId(), "type", "DIRECTORY");
		returnMap.put("directorys",  dataCenterService.findByMap(map, null, null));
		
		return new JsonResult(1, returnMap, page, 
				Tools.getMap("crumbs", Tools.getCrumbs("目录列表:"+directoryName,"void")));
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority = Const.AUTH_SOURCE)
	public JsonResult addOrUpdate(@ModelAttribute Source source) throws Exception{
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
			
			String docContent = GetTextFromFile.getText(source.getFilePath());
			
			//如果备注为空，则提取文档内容前2500 个字
			if( MyString.isEmpty(source.getRemark()) ){
				source.setRemark(docContent.length() > 2500? docContent.substring(0, 2500) +" ... \r\n..." : docContent);
			}
			source.setUpdateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm_ss));
			if(!MyString.isEmpty(source.getId())){
				sourceService.update(source);
			}else{
				source.setId(null);
				sourceService.save(source);
			}
			
			SearchDto searchDto = source.toSearchDto();
			//索引内容 = 备注内容 + 文档内容
			searchDto.setContent(searchDto.getContent() + docContent);
			GetBeanBySetting.getSearchService().update(searchDto);
			return new JsonResult(1,source);
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport(authority = Const.AUTH_SOURCE)
	public JsonResult delete(@ModelAttribute Source source) throws MyException, IOException{
		sourceService.delete(source);
		GetBeanBySetting.getSearchService().delete(new SearchDto(source.getId()));
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) {
		Source change = sourceService.get(changeId);
		model = sourceService.get(id);
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		sourceService.update(model);
		sourceService.update(change);
		return new JsonResult(1, null);
	}

}
