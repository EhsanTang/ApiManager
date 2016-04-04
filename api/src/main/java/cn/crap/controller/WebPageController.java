package cn.crap.controller;

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
import cn.crap.inter.service.IWebPageService;
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
	private IWebPageService webPageService;

	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute WebPage webPage,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("name|like",webPage.getName(),"moduleId",webPage.getModuleId(),"type",webPage.getType());
		return new JsonResult(1,webPageService.findByMap(map,page,null),page);
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
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute WebPage webPage) throws MyException{
		if(webPage.getType().equals(WebPageType.DICTIONARY.name())){
			Tools.hasAuth(Const.AUTH_DICTIONARY, request.getSession(), webPage.getModuleId());
		}else{
			Tools.hasAuth(Const.AUTH_WEBPAGE, request.getSession(), "");
		}
		if(!MyString.isEmpty(webPage.getId())){
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
		webPageService.delete(webPage);
		return new JsonResult(1,null);
	}

}
