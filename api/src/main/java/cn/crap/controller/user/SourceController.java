package cn.crap.controller.user;

import cn.crap.adapter.SourceAdapter;
import cn.crap.dto.SearchDto;
import cn.crap.dto.SourceDto;
import cn.crap.enu.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.model.Source;
import cn.crap.query.SourceQuery;
import cn.crap.service.ISearchService;
import cn.crap.service.SourceService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user/source")
public class SourceController extends BaseController{
	@Autowired
	private SourceService sourceService;
	@Autowired
	private ISearchService luceneService;
	/**
	 * 
	 * @return
	 * @throws MyException 
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute SourceQuery query) throws MyException{
        Project project = getProject(query);
        checkPermission(project, READ);

		Page page= new Page(query);
		page.setAllRow(sourceService.count(query));
		List<SourceDto> sourceDtos = SourceAdapter.getDto(sourceService.query(query));

		return new JsonResult().data(sourceDtos).page(page);
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Source source) throws MyException{
		Source model;
        Module module;
        Project project;
		if(!MyString.isEmpty(source.getId())){
			model = sourceService.getById(source.getId());
            module = moduleCache.get(model.getModuleId());
			checkPermission( model.getProjectId(), READ);
		}else{
			model=new Source();
			model.setModuleId(source.getModuleId());
            module = moduleCache.get(source.getModuleId());
            if (module == null || module.getId() == null){
                module = null;
                project = projectCache.get(source.getProjectId());
                model.setProjectId(project.getId());
            }
		}
		return new JsonResult(1, SourceAdapter.getDto(model, module));
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute SourceDto dto) throws Exception{
        Assert.notNull(dto.getProjectId(), "projectId can't be null");
        Assert.notNull(dto.getFilePath(), MyError.E000016.getMessage());

        String id = dto.getId();
        String newProjectId = getProjectId(dto.getProjectId(), dto.getModuleId());
        dto.setProjectId(newProjectId);
        Source source = SourceAdapter.getModel(dto);

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

        if(id != null){
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

            checkPermission(source.getProjectId(), MOD_SOURCE);
            sourceService.update(source, true);
        }else {
            checkPermission(source.getProjectId(), ADD_SOURCE);
            sourceService.insert(source);
            id = source.getId();
        }

        SearchDto searchDto = SourceAdapter.getSearchDto(sourceService.getById(id));
        luceneService.update(searchDto);
        return new JsonResult(1, source);
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
			checkPermission(sourceService.getById( tempId ).getProjectId(), DEL_SOURCE);
			Source source = new Source();
			source.setId(tempId);
			
			sourceService.delete(tempId);
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
		checkPermission(change.getModuleId(), MOD_SOURCE);
		checkPermission(model.getModuleId(), MOD_SOURCE);
				
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		sourceService.update(model);
		sourceService.update(change);
		return new JsonResult(1, null);
	}

}
