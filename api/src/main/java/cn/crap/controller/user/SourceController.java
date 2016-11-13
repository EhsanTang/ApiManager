package cn.crap.controller.user;

import java.io.IOException;

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
import cn.crap.inter.service.table.ISourceService;
import cn.crap.inter.service.tool.ISearchService;
import cn.crap.model.Source;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/user/source")
public class SourceController extends BaseController<Source>{

	@Autowired
	private ISourceService sourceService;
	@Autowired
	private ISearchService luceneService;
	/**
	 * 
	 * @param source
	 * @param currentPage 当前页
	 * @param pageSize 每页显示多少条，-1表示查询全部
	 * @return
	 * @throws MyException 
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Source source,@RequestParam(defaultValue="1") int currentPage) throws MyException{
		hasPermissionModuleId((source.getModuleId()), view);
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		return new JsonResult(1, sourceService.findByMap(Tools.getMap("name|like", source.getName(), "moduleId", source.getModuleId()), page, null), page);
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Source source) throws MyException{
		Source model;
		if(!MyString.isEmpty(source.getId())){
			model = sourceService.get(source.getId());
			hasPermissionModuleId( model.getModuleId(), view);
		}else{
			model=new Source();
			model.setModuleId(source.getModuleId());
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
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
			
			SearchDto searchDto = source.toSearchDto(cacheService);
			source.setUpdateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm_ss));
			if(!MyString.isEmpty(source.getId())){
				hasPermissionModuleId((source.getModuleId()), modSource);
				sourceService.update(source);
			}else{
				hasPermissionModuleId((source.getModuleId()), addSource);
				sourceService.save(source);
			}
			// 新增的source没有id，必须在持久化后从新设置id
			searchDto.setId(source.getId());
			luceneService.update(searchDto);
			return new JsonResult(1,source);
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport
	public JsonResult delete(@ModelAttribute Source source) throws MyException, IOException{
		// 权限
		hasPermissionModuleId(sourceService.get(source.getId()).getModuleId(), delSource);
		
		sourceService.delete(source);
		luceneService.delete(new SearchDto(source.getId()));
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Source change = sourceService.get(changeId);
		Source model = sourceService.get(id);
		// 权限
		hasPermissionModuleId(change.getModuleId(), modSource);
		hasPermissionModuleId(model.getModuleId(), modSource);
				
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		sourceService.update(model);
		sourceService.update(change);
		return new JsonResult(1, null);
	}

}
