package cn.crap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.crap.framework.MyException;
import cn.crap.framework.JsonResult;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.IModuleService;
import cn.crap.inter.service.IWebPageService;
import cn.crap.model.Module;
import cn.crap.model.WebPage;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
import cn.crap.utils.WebPageType;

@Scope("prototype")
@Controller
@RequestMapping("/webPage")
public class WebPageController extends BaseController<WebPage>{
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private IWebPageService webPageService;

	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute WebPage webPage,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("name|like",webPage.getName(),"moduleId",webPage.getModuleId(),"type",webPage.getType());
		return new JsonResult(1,webPageService.findByMap(map,page,null),page,WebPageType.valueOf(webPage.getType()).getName());
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute WebPage webPage){
		model= webPageService.get(webPage.getId());
		if(model==null){
			model=new WebPage();
			model.setType(webPage.getType());
			model.setModuleId(webPage.getModuleId());
		}
		return new JsonResult(1,model);
	}
	@RequestMapping("/webDetail.do")
	@ResponseBody
	public JsonResult webDetail(@ModelAttribute WebPage webPage,String password,String visitCode) throws MyException{
		if(webPage.getId().length()<21){
			map = Tools.getMap("key", webPage.getId());
			List<WebPage>models=webPageService.findByMap(map, null, null);
			if(models.size()>0)
				model = models.get(0);
		}
		if(model==null){
			model= webPageService.get(webPage.getId());
		}
		if(model.getType().equals(WebPageType.DICTIONARY.name())){
			Module module = moduleService.get(model.getModuleId());
			Tools.canVisitModule(module.getPassword(), password, visitCode, request);
		}
		return new JsonResult(1,model);
	}
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute WebPage webPage) throws MyException{
		if(webPage.getType().equals(WebPageType.DICTIONARY.name())){
			Tools.hasAuth(Const.AUTH_DICTIONARY, request.getSession(), webPage.getModuleId());
		}else{
			Tools.hasAuth(Const.AUTH_WEBPAGE, request.getSession(), "");
		}
		if(MyString.isEmpty(webPage.getKey())){
			webPage.setKey(null);
		}
		webPage.setCanDelete(Byte.valueOf("1"));
		if(!MyString.isEmpty(webPage.getId())){
			/**
			 * 判断是否为系统数据，系统数据不允许修改可以和canDelete字段
			 */
			model = webPageService.get(webPage.getId());
			if(model.getCanDelete()!=1){
				webPage.setKey(model.getKey());
				webPage.setCanDelete(Byte.valueOf("0"));
			}
			webPageService.update(webPage);
		}else{
			webPage.setId(null);
			webPageService.save(webPage);
		}
		return new JsonResult(1,webPage);
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute WebPage webPage) throws MyException{
		webPage = webPageService.get(webPage.getId());
		if(webPage.getType().equals(WebPageType.DICTIONARY.name()))
			Tools.hasAuth(Const.AUTH_DICTIONARY, request.getSession(), webPage.getModuleId());
		else
			Tools.hasAuth(Const.AUTH_WEBPAGE, request.getSession(), webPage.getModuleId());
		model = webPageService.get(webPage.getId());
		if(model.getCanDelete()!=1){
			throw new MyException("000009");
		}
		webPageService.delete(webPage);
		return new JsonResult(1,null);
	}

}
