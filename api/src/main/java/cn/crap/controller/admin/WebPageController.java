package cn.crap.controller.admin;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.SearchDto;
import cn.crap.enumeration.WebPageType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.ISearchService;
import cn.crap.inter.service.IWebPageService;
import cn.crap.model.WebPage;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
public class WebPageController extends BaseController<WebPage>{
	@Autowired
	private IWebPageService webPageService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private ISearchService luceneService;

	@RequestMapping("/webPage/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute WebPage webPage,@RequestParam(defaultValue="1") Integer currentPage){
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		
		Map<String,Object> map = Tools.getMap("name|like",webPage.getName(),"moduleId",webPage.getModuleId(),"type", webPage.getType(),"category",webPage.getCategory());
		
		return new JsonResult(1,webPageService.findByMap(map, " new WebPage(id, type, name, click, category, createTime, key, moduleId, brief, sequence) ", page,null), page,
				Tools.getMap("type", WebPageType.valueOf(webPage.getType()).getName(), "category", webPage.getCategory(), "crumbs", 
						Tools.getCrumbs(MyString.isEmpty(webPage.getCategory()) ? WebPageType.valueOf( webPage.getType()).getName() : webPage.getCategory(), "void")));
	}
	
	@RequestMapping("/webPage/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute WebPage webPage){
		WebPage model;
		if(!webPage.getId().equals(Const.NULL_ID)){
			model= webPageService.get(webPage.getId());
		}else{
			model=new WebPage();
			model.setType(webPage.getType());
			model.setModuleId(webPage.getModuleId());
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/webPage/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute WebPage webPage) throws MyException, IOException{
		if(MyString.isEmpty(webPage.getModuleId())){
			webPage.setModuleId(Const.TOP_MODULE);
		}
		
		Tools.hasAuth(WebPageType.valueOf(webPage.getType()).name() +"_" + Const.MODULEID,  webPage.getModuleId());
		
		if(MyString.isEmpty(webPage.getKey())){
			webPage.setKey(null);
		}
		
		webPage.setCanDelete(Byte.valueOf("1"));
		if(!MyString.isEmpty(webPage.getId())){
			/**
			 * 判断是否为系统数据，系统数据不允许修改可以和canDelete字段
			 */
			WebPage model = webPageService.get(webPage.getId());
			if(model.getCanDelete()!=1){
				webPage.setKey(model.getKey());
				webPage.setCanDelete(Byte.valueOf("0"));
			}
			webPageService.update(webPage);
			luceneService.update(webPage.toSearchDto(null));
		}else{
			webPage.setId(null);
			webPageService.save(webPage);
			luceneService.add(webPage.toSearchDto(null));
		}
		cacheService.delObj(Const.CACHE_WEBPAGE + webPage.getId());
		cacheService.delObj(Const.CACHE_WEBPAGE + webPage.getKey());
		return new JsonResult(1,webPage);
	}
	
	@RequestMapping("/webPage/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute WebPage webPage) throws MyException, IOException{
		WebPage model = webPageService.get(webPage.getId());
		Tools.hasAuth(WebPageType.valueOf(model.getType()).name() + "_" + Const.MODULEID,  model.getModuleId());
		if(model.getCanDelete()!=1){
			throw new MyException("000009");
		}
		webPageService.delete(webPage);
		cacheService.delObj(Const.CACHE_WEBPAGE + webPage.getId());
		cacheService.delObj(Const.CACHE_WEBPAGE + webPage.getKey());
		
		luceneService.delete(new SearchDto(webPage.getId()));
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/back/webPage/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) {
		WebPage change = webPageService.get(changeId);
		WebPage model = webPageService.get(id);
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		webPageService.update(model);
		webPageService.update(change);
		return new JsonResult(1, null);
	}

	@RequestMapping("/webPage/markdown.do")
	public String markdown(@ModelAttribute WebPage webPage) throws Exception {
		WebPage model;
		if(!webPage.getId().equals(Const.NULL_ID)){
			model= webPageService.get(webPage.getId());
		}else{
			model=new WebPage();
			model.setType(webPage.getType());
			model.setModuleId(webPage.getModuleId());
		}
		request.setAttribute("markdownPreview", model.getContent());
		request.setAttribute("markdownText", model.getMarkdown());
		return "/WEB-INF/views/markdown.jsp";
	}

}
