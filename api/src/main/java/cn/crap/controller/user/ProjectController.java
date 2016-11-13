package cn.crap.controller.user;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enumeration.ProjectStatus;
import cn.crap.enumeration.ProjectType;
import cn.crap.enumeration.UserType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.IErrorService;
import cn.crap.inter.service.table.IMenuService;
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.table.IProjectUserService;
import cn.crap.inter.service.table.IRoleService;
import cn.crap.inter.service.table.IUserService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Article;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.model.Setting;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.HttpPostGet;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/user/project")
public class ProjectController extends BaseController<Project> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private IErrorService errorService;
	@Autowired
	private IProjectUserService projectUserService;
	@Autowired
	private Config config;
	@Autowired
	private IArticleService articleService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Project project, @RequestParam(defaultValue="1") int currentPage, 
			@RequestParam(defaultValue="false") boolean myself) throws MyException{
		
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		
		// 普通用户，管理员我的项目菜单只能查看自己的项目
		LoginInfoDto user = Tools.getUser();
		if( Tools.getUser().getType() == UserType.USER.getType() || myself){
			if(MyString.isEmpty(project.getName())){
				return new JsonResult(1, 
						projectService.queryByHql("from Project where userId=:userId or id in (select projectId from ProjectUser where userId=:userId)", Tools.getMap("userId", user.getId()), page)
						, page);

			}else{
				return new JsonResult(1, 
						projectService.queryByHql("from Project where (userId=:userId or id in (select projectId from ProjectUser where userId=:userId)) and name like :name", 
						Tools.getMap("userId", user.getId(), "name|like", project.getName()), page)
						, page);

			}
		}else{
			Map<String,Object> map = null;
			map = Tools.getMap("name|like", project.getName());

			return new JsonResult(1, projectService.findByMap(map, page, null), page);
		}
		
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Project project) throws MyException{
		Project model;
		if(!project.getId().equals(Const.NULL_ID)){
			model= cacheService.getProject(project.getId());
			hasPermission(model);
		}else{
			model=new Project();
		}
		return new JsonResult(1,model);
	}
	
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute Project project) throws Exception{
		// 系统数据，不允许删除
		if(project.getId().equals("web"))
			throw new MyException("000009");
		
		Project model;
		LoginInfoDto user = Tools.getUser();
		// 修改
		if(!MyString.isEmpty(project.getId())){
			model= cacheService.getProject(project.getId());
			hasPermission(model);
			
			// 不允许转移项目
			project.setUserId(model.getUserId());
			
			// 普通用户不能推荐项目，将项目类型修改为原有类型
			if( Tools.getUser().getType() == UserType.USER.getType()){
				project.setStatus(model.getStatus());
			}
						
			projectService.update(project);
		}
		
		// 新增
		else{
			project.setUserId(user.getId());
			// 普通用户不能推荐项目
			if( Tools.getUser().getType() == UserType.USER.getType()){
				project.setStatus(Byte.valueOf(ProjectStatus.COMMON.getStatus()+""));
			}
			
			projectService.save(project);
		}
		
		// 清楚缓存
		cacheService.delObj(Const.CACHE_PROJECT+project.getId());
		
		// 刷新用户权限 将用户信息存入缓存
		cacheService.setObj(Const.CACHE_USER + user.getId(), new LoginInfoDto(userService.get(user.getId()), roleService, projectService, projectUserService), config.getLoginInforTime());
		return new JsonResult(1,project);
	}
	
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Project project) throws Exception{
		// 系统数据，不允许删除
		if(project.getId().equals("web"))
			throw new MyException("000009");

				
		Project model= cacheService.getProject(project.getId());
		hasPermission(model);
		
		
		// 只有子模块数量为0，才允许删除项目
		if(moduleService.getCount(Tools.getMap("projectId", model.getId())) > 0){
			throw new MyException("000023");
		}
		
		// 只有错误码数量为0，才允许删除项目
		if(errorService.getCount(Tools.getMap("projectId", model.getId())) > 0){
			throw new MyException("000033");
		}
		
		// 只有项目成员数量为0，才允许删除项目
		if(projectUserService.getCount(Tools.getMap("projectId", model.getId()))>0){
			throw new MyException("000038");
		}
		
		cacheService.delObj(Const.CACHE_PROJECT+project.getId());
		projectService.delete(project);
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Project change = cacheService.getProject(changeId);
		Project model = cacheService.getProject(id);
		
		hasPermission(change);
		hasPermission(model);
		
		int modelSequence = model.getSequence();
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		projectService.update(model);
		projectService.update(change);

		return new JsonResult(1, null);
	}
	
	/**
	 * 静态化模块
	 */
	@RequestMapping("/staticizeModule.do")
	public ModelAndView staticizeModule(HttpServletRequest req, @RequestParam String moduleId) throws MyException {
		Module module = cacheService.getModule(moduleId);
		Project project = cacheService.getProject(module.getProjectId());
		String path = Tools.getServicePath(req) + "resources/html/staticize/"+project.getId(); 

		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException("000044");
		}
		
		// 静态化
		Map<String, String> settingMap = new HashMap<String, String>();
		for (Setting setting : cacheService.getSetting()) {
			settingMap.put(setting.getKey(), setting.getValue());
		}
		settingMap.put(Const.DOMAIN, config.getDomain());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> map = Tools.getMap("projectId",project.getId());
		returnMap.put("settings", settingMap);
		returnMap.put("project", project);
		returnMap.put("module", module);
		returnMap.put("moduleList", moduleService.findByMap(map, null, null));
		returnMap.put("menuList", menuService.getLeftMenu(null));
		returnMap.put("keywords", module.getRemark());
		returnMap.put("description", project.getRemark());
		returnMap.put("title", module.getName());
		
		map = Tools.getMap("moduleId", moduleId, "type", "ARTICLE");
		List<Article> articleList = articleService.findByMap(map, " new Article(id, type, name, click, category, createTime, key, moduleId, brief, sequence) ", null, null);
		returnMap.put("articleList", articleList);
		
		return new ModelAndView("WEB-INF/views/staticize/index.jsp",returnMap);
	}
	/**
	 * 静态化文章
	 * @param req
	 * @param articleId
	 * @return
	 * @throws MyException
	 */
	@RequestMapping("/staticizeArticle.do")
	public ModelAndView staticizeArticle(HttpServletRequest req, @RequestParam String articleId) throws MyException {
		Article article = articleService.get(articleId);
		Module module = cacheService.getModule(article.getModuleId());
		Project project = cacheService.getProject(module.getProjectId());
		String path = Tools.getServicePath(req) + "resources/html/staticize/"+project.getId(); 

		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException("000044");
		}
		
		// 静态化
		Map<String, String> settingMap = new HashMap<String, String>();
		for (Setting setting : cacheService.getSetting()) {
			settingMap.put(setting.getKey(), setting.getValue());
		}
		settingMap.put(Const.DOMAIN, config.getDomain());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("settings", settingMap);
		returnMap.put("project", project);
		returnMap.put("module", module);
		returnMap.put("article", article);
		returnMap.put("moduleList", moduleService.findByMap(Tools.getMap("projectId",project.getId()), null, null));
		returnMap.put("menuList", menuService.getLeftMenu(null));
		returnMap.put("keywords", module.getRemark());
		returnMap.put("description", article.getBrief());
		returnMap.put("title", article.getName());
		
		return new ModelAndView("WEB-INF/views/staticize/articleDetail.jsp",returnMap);
	}
	
	
	
	/**
	 * 静态化
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/staticize.do")
	@ResponseBody
	public JsonResult staticize(HttpServletRequest req, @RequestParam String projectId) throws UnsupportedEncodingException, Exception {
		
		Project project = cacheService.getProject(projectId);
		String path = Tools.getServicePath(req) + "resources/html/staticize/"+project.getId(); 

		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException("000044");
		}
		for(Module module : moduleService.findByMap(Tools.getMap("projectId", projectId), null, null)){
			// 创建文件夹
			Tools.createFile(path + "/" + module.getId());
			
			// 静态化模块
			String html = HttpPostGet.get(config.getDomain()+ "/user/project/staticizeModule.do?moduleId="+ module.getId(), null, null, 10 * 1000);
			Tools.staticize(html, path + "/" +  module.getId() +"/list.html");
			
			// 静态化文章
			for(Article article: articleService.findByMap(Tools.getMap("moduleId", module.getId()), null, null)){
				html = HttpPostGet.get(config.getDomain()+ "/user/project/staticizeArticle.do?articleId="+ article.getId(), null, null, 10 * 1000);
				Tools.staticize(html, path + "/" +  module.getId() +"/"+article.getId()+".html");
			}
		}
		return new JsonResult(1, null );
		
	}
}
