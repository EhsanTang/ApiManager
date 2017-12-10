package cn.crap.controller.front;

import java.util.List;
import java.util.Map;

import cn.crap.adapter.SourceAdapter;
import cn.crap.dto.SourceDto;
import cn.crap.model.mybatis.*;
import cn.crap.service.mybatis.custom.CustomSourceService;
import cn.crap.service.mybatis.imp.MybatisSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.service.ICacheService;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.Project;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller("frontSourceController")
@RequestMapping("/front/source")
public class SourceController extends BaseController{

	@Autowired
	private MybatisSourceService sourceService;
	@Autowired
	private CustomSourceService customSourceService;
	@Autowired
	private ICacheService cacheService;
	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult webDetail(@ModelAttribute Source source,String password,String visitCode) throws MyException{
		Source model;
		if(!MyString.isEmpty(source.getId())){
			model = sourceService.selectByPrimaryKey(source.getId());
		}else{
			throw new MyException("000020");
		}
		
		Module module = cacheService.getModule(model.getModuleId());
		Project project = cacheService.getProject(module.getProjectId());
		
		// 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		isPrivateProject(password, visitCode, project);
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult webList(@ModelAttribute Source source,@RequestParam(defaultValue="1") int currentPage,String password,String visitCode) throws MyException{
		Module module = cacheService.getModule(source.getModuleId());
		Project project = cacheService.getProject(module.getProjectId());
		
		// 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		isPrivateProject(password, visitCode, project);

		// TODO page 中放入DTO，不直接返回page即可
		Page<Source> page= new Page(15, currentPage);
		List<SourceDto> sourceDtoList = SourceAdapter.getDto(page.getList());
		page = customSourceService.queryByModuleId(source.getModuleId(), source.getName(), page);
		return new JsonResult(1, sourceDtoList, page, Tools.getMap("crumbs", Tools.getCrumbs("模块:"+module.getName(),"void")));
	}
}
