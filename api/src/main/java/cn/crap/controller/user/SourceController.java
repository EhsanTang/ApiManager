package cn.crap.controller.user;

import cn.crap.adapter.SourceAdapter;
import cn.crap.dto.SearchDto;
import cn.crap.enumer.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.Source;
import cn.crap.model.mybatis.SourceCriteria;
import cn.crap.service.ISearchService;
import cn.crap.service.custom.CustomSourceService;
import cn.crap.service.mybatis.SourceService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/user/source")
public class SourceController extends BaseController{

	@Autowired
	private SourceService sourceService;
	@Autowired
	private CustomSourceService customSourceService;
	@Autowired
	private ISearchService luceneService;
	/**
	 * 
	 * @param currentPage 当前页
	 * @return
	 * @throws MyException 
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@RequestParam String moduleId, String name, @RequestParam(defaultValue="1") int currentPage) throws MyException{
		checkUserPermissionByModuleId((moduleId), VIEW);
		Page page= new Page(15, currentPage);

		SourceCriteria example = new SourceCriteria();
		SourceCriteria.Criteria criteria = example.createCriteria().andModuleIdEqualTo(moduleId);
		if (MyString.isNotEmpty(name)){
			criteria.andNameLike('%' + name + '%');
		}

		example.setLimitStart(page.getStart());
		example.setMaxResults(page.getSize());
		example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
		page.setAllRow(sourceService.countByExample(example));

		return new JsonResult(1, SourceAdapter.getDto(sourceService.selectByExample(example)), page);
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Source source) throws MyException{
		Source model;
        Module module;
		if(!MyString.isEmpty(source.getId())){
			model = sourceService.getById(source.getId());
            module = moduleCache.get(model.getModuleId());
			checkUserPermissionByModuleId( model.getModuleId(), VIEW);
		}else{
			model=new Source();
			model.setModuleId(source.getModuleId());
            module = moduleCache.get(source.getModuleId());
		}
		return new JsonResult(1, SourceAdapter.getDto(model, module));
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute Source source) throws Exception{
			if(MyString.isEmpty(source.getFilePath())){
				throw new MyException(MyError.E000016);
			}
			
			// 判断版本号是否正确 .CAV.文件标识.版本号 
			if( !source.getFilePath().contains(".CAV.") ){
				throw new MyException(MyError.E000017);
			}
			
			// 校验文件名是否正确
			String vsesions[];
			try{
				vsesions = source.getFilePath().split("\\.");
				if( !vsesions[vsesions.length-4].equals("CAV") ){
					throw new MyException(MyError.E000017);
				}
	  			Long.parseLong(vsesions[vsesions.length-2]);
			}catch(Exception e){
				throw new MyException(MyError.E000017);
			}
			
			if(!MyString.isEmpty(source.getId())){
				Source oldSource = sourceService.getById(source.getId());
				String oldVsesions[]  = oldSource.getFilePath().split("\\.");
				if( !oldVsesions[oldVsesions.length-3].equals(vsesions[vsesions.length-3])){
					// 文件标识有误
					throw new MyException(MyError.E000018);
				}
					
				// 新版本号必须 >= 旧版本号
				if( (Long.parseLong(oldVsesions[oldVsesions.length-2])) > Long.parseLong(vsesions[vsesions.length-2]) ){
						// 文件标识有误
					throw new MyException(MyError.E000019);
				}else if((Long.parseLong(oldVsesions[oldVsesions.length-2])) == Long.parseLong(vsesions[vsesions.length-2]) ){
					// 新旧版本号一致，不更新文档地址
					source.setFilePath(oldSource.getFilePath());
				}
			}
			
			SearchDto searchDto = SourceAdapter.getSearchDto(source);
			source.setUpdateTime(new Date());
			if(!MyString.isEmpty(source.getId())){
				checkUserPermissionByModuleId((source.getModuleId()), MOD_SOURCE);
				customSourceService.update(source, "资源", "");

			}else{
				checkUserPermissionByModuleId((source.getModuleId()), ADD_SOURCE);
				sourceService.insert(source);
			}
			// 新增的source没有id，必须在持久化后从新设置id
			searchDto.setId(source.getId());
			luceneService.update(searchDto);
			return new JsonResult(1,source);
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport
	public JsonResult delete(String id, String ids) throws MyException, IOException{
		if( MyString.isEmpty(id) && MyString.isEmpty(ids)){
			throw new MyException(MyError.E000029);
		}
		if( MyString.isEmpty(ids) ){
			ids = id;
		}
		
		for(String tempId : ids.split(",")){
			if(MyString.isEmpty(tempId)){
				continue;
			}
			// 权限
			checkUserPermissionByModuleId(sourceService.getById( tempId ).getModuleId(), DEL_SOURCE);
			Source source = new Source();
			source.setId(tempId);
			
			customSourceService.delete(tempId, "资源", "");
			luceneService.delete(new SearchDto(source.getId()));
		}
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Source change = sourceService.getById(changeId);
		Source model = sourceService.getById(id);
		// 权限
		checkUserPermissionByModuleId(change.getModuleId(), MOD_SOURCE);
		checkUserPermissionByModuleId(model.getModuleId(), MOD_SOURCE);
				
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		sourceService.update(model);
		sourceService.update(change);
		return new JsonResult(1, null);
	}

}
