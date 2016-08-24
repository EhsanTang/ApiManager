package cn.crap.controller.back;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import cn.crap.inter.service.ICommentService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IWebPageService;
import cn.crap.model.Comment;
import cn.crap.model.DataCenter;
import cn.crap.model.WebPage;
import cn.crap.utils.Config;
import cn.crap.utils.Const;
import cn.crap.utils.GetBeanBySetting;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/webPage")
public class BackWebPageController extends BaseController<WebPage>{
	@Autowired
	private IDataCenterService moduleService;
	@Autowired
	private IWebPageService webPageService;
	@Autowired
	private ICommentService commentService;
	@Autowired
	private ICacheService cacheService;

	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute WebPage webPage,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		
		map = Tools.getMap("name|like",webPage.getName(),"moduleId",webPage.getModuleId(),"type", webPage.getType(),"category",webPage.getCategory());
		
		return new JsonResult(1,webPageService.findByMap(map, " new WebPage(id, type, name, click, category, createTime, key, moduleId, brief) ", page,null), page,
				Tools.getMap("type", WebPageType.valueOf(webPage.getType()).getName(), "category", webPage.getCategory(), "crumbs", 
						Tools.getCrumbs(MyString.isEmpty(webPage.getCategory()) ? WebPageType.valueOf( webPage.getType()).getName() : webPage.getCategory(), "void")));
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
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
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute WebPage webPage) throws MyException, IOException{
		if(webPage.getType().equals(WebPageType.DICTIONARY.name())){
			Tools.hasAuth(Const.AUTH_DICTIONARY,  webPage.getModuleId());
		}else{
			Tools.hasAuth(WebPageType.valueOf(webPage.getType()).name(),  "");
		}
		if(MyString.isEmpty(webPage.getKey())){
			webPage.setKey(null);
		}
		if(MyString.isEmpty(webPage.getModuleId())){
			webPage.setModuleId(Const.TOP_MODULE);
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
			GetBeanBySetting.getSearchService().update(webPage.toSearchDto());
		}else{
			webPage.setId(null);
			webPageService.save(webPage);
			GetBeanBySetting.getSearchService().add(webPage.toSearchDto());
		}
		return new JsonResult(1,webPage);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute WebPage webPage) throws MyException, IOException{
		webPage = webPageService.get(webPage.getId());
		if(webPage.getType().equals(WebPageType.DICTIONARY.name()))
			Tools.hasAuth(Const.AUTH_DICTIONARY,  webPage.getModuleId());
		else
			Tools.hasAuth(WebPageType.valueOf(webPage.getType()).name(),  "");
		model = webPageService.get(webPage.getId());
		if(model.getCanDelete()!=1){
			throw new MyException("000009");
		}
		webPageService.delete(webPage);
		GetBeanBySetting.getSearchService().delete(new SearchDto(webPage.getId()));
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
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

	@RequestMapping("/markdown.do")
	public String markdown(@ModelAttribute WebPage webPage) throws Exception {
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
