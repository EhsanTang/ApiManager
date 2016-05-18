package cn.crap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICommentService;
import cn.crap.inter.service.IModuleService;
import cn.crap.inter.service.IWebPageService;
import cn.crap.model.Comment;
import cn.crap.model.Module;
import cn.crap.model.WebPage;
import cn.crap.utils.Cache;
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
	@Autowired
	private ICommentService commentService;

	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute WebPage webPage,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("name|like",webPage.getName(),"moduleId",webPage.getModuleId(),"type",webPage.getType(),"category",webPage.getCategory());
		return new JsonResult(1,webPageService.findByMap(map,page,null),page,Tools.getMap("type",WebPageType.valueOf(webPage.getType()).getName(),"category",webPage.getCategory()));
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute WebPage webPage){
		if(!webPage.getId().equals(Const.NULL_ID)){
			model= webPageService.get(webPage.getId());
		}else{
			model=new WebPage();
			model.setType(webPage.getType());
			model.setModuleId(webPage.getModuleId());
		}
		return new JsonResult(1,model);
	}
	@RequestMapping("/webDetail.do")
	@ResponseBody
	public JsonResult webDetail(@ModelAttribute WebPage webPage,String password,String visitCode) throws MyException{
		
		// 根据key查询webPage
		if(webPage.getId().length()<21){
			map = Tools.getMap("key", webPage.getId());
			List<WebPage>models=webPageService.findByMap(map, null, null);
			if(models.size()>0)
				model = models.get(0);
		}
		// 根据key没有查到，则根据id查
		if(model==null){
			model= webPageService.get(webPage.getId());
		}
		
		if(model.getType().equals(WebPageType.DICTIONARY.name())){
			Module module = moduleService.get(model.getModuleId());
			Tools.canVisitModule(module.getPassword(), password, visitCode, request);
		}
		returnMap.put("comment", new Comment(model.getId()));
		map = Tools.getMap("webpageId", model.getId());
		returnMap.put("comments", commentService.findByMap(map, null, null));
		returnMap.put("commentCode", Cache.getSetting(Const.SETTING_COMMENTCODE).getValue());
		
		// 更新点击量
		webPageService.update("update WebPage set click=click+1 where id=:id", Tools.getMap("id", model.getId()));
		return new JsonResult(1,model, null, returnMap);
	}
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute WebPage webPage) throws MyException{
		if(webPage.getType().equals(WebPageType.DICTIONARY.name())){
			Tools.hasAuth(Const.AUTH_DICTIONARY, request.getSession(), webPage.getModuleId());
		}else{
			Tools.hasAuth(WebPageType.valueOf(webPage.getType()).name(), request.getSession(), "");
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
			Tools.hasAuth(WebPageType.valueOf(webPage.getType()).name(), request.getSession(), "");
		model = webPageService.get(webPage.getId());
		if(model.getCanDelete()!=1){
			throw new MyException("000009");
		}
		webPageService.delete(webPage);
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	@Override
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) {
		WebPage change = webPageService.get(changeId);
		model = webPageService.get(id);
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		webPageService.update(model);
		webPageService.update(change);
		return new JsonResult(1, null);
	}


}
