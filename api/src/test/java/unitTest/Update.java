package unitTest;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.crap.enumeration.ArticleType;
import cn.crap.enumeration.ProjectStatus;
import cn.crap.enumeration.ProjectType;
import cn.crap.framework.MyException;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.IInterfaceService;
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.table.IRoleService;
import cn.crap.inter.service.table.ISourceService;
import cn.crap.inter.service.table.IUserService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Article;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:springMVC.xml"})
public class Update {
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IInterfaceService interfaceService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IArticleService articleService;
	@Autowired
	private ISourceService sourceService;
	
	/**
	 * V6版本升级成V7版本，下载源代码后，请执行该方法升级数据库
	 * 需要提供接口（有些用户没有下载源码，直接安装的无法执行该代码）
	 * @throws IOException 
	 * @throws MyException 
	 */
	@Test
	public void v6ToV7() throws MyException, IOException{
		for(Module module : moduleService.findByMap(null, null,null)){
			// 请除无效module
			if(MyString.isEmpty(module.getProjectId())){
				moduleService.delete(module);
			}else{
				Project project = projectService.get(module.getProjectId());
				Module p = moduleService.get(module.getProjectId());
				if(MyString.isEmpty(project.getId()) && !MyString.isEmpty(p.getId())){
					// 新建项目
					project.setId(p.getId());
					project.setCreateTime(p.getCreateTime());
					project.setName(p.getName());
					project.setRemark(p.getRemark());
					project.setSequence(p.getSequence());
					project.setStatus(ProjectStatus.COMMON.getStatus());
					project.setType(ProjectType.PRIVATE.getType());
					project.setUserId(p.getUserId());
					projectService.save(project);
					projectService.update("update Project set id='"+p.getId()+"' where id = '"+project.getId()+"'", null);
				}
			}
			
		}
		// 同步数据字典，从项目迁移到模块，项目下没有模块则不迁移
		for(Article article: articleService.findByMap(Tools.getMap("type", ArticleType.DICTIONARY.name()), null, null)){
			Project p = projectService.get(article.getModuleId());
			if(MyString.isEmpty(p.getId())){
				articleService.delete(article);
			}else{
				List<Module> modules = moduleService.findByMap(Tools.getMap("projectId", p.getId()), null, null);
				if(modules.size()>0){
					article.setModuleId(modules.get(0).getId());
					articleService.update(article);
				}
			}
		}
		
		// 删除模块中的项目
		for(Module module : moduleService.findByMap(null, null,null)){
			if(MyString.isEmpty(module.getProjectId())){
				moduleService.delete(module);
			}
		}
		
	}
		
}
