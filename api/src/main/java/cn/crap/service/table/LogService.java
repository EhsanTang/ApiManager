package cn.crap.service.table;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.enumeration.LogType;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.dao.IInterfaceDao;
import cn.crap.inter.dao.ILogDao;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.ILogService;
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.table.ISourceService;
import cn.crap.model.Article;
import cn.crap.model.Interface;
import cn.crap.model.Log;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.model.Source;
import cn.crap.utils.MyString;
import net.sf.json.JSONObject;

@Service
public class LogService extends BaseService<Log>
		implements ILogService {
	@Autowired
	private IInterfaceDao interfaceDao;
	@Autowired
	private ILogDao logDao;
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IArticleService articleService;
	@Autowired
	private ISourceService sourceService;
	
	@Resource(name="logDao")
	public void setDao(IBaseDao<Log> dao) {
		super.setDao(dao);
	}

	@Override
	@Transactional
	public Log get(String id){
		Log model = logDao.get(id);
		if(model == null)
			 return new Log();
		return model;
	}
	
	
	@Override
	@Transactional
	public void recover(Log log) throws MyException{
		log = get(log.getId());
		switch(log.getModelClass().toUpperCase()){
		
			case "INTERFACE"://恢复接口
				JSONObject json = JSONObject.fromObject(log.getContent());
				Interface inter = (Interface) JSONObject.toBean(json,Interface.class);
				checkModuleAndProject(inter.getModuleId());
				interfaceDao.update(inter);
				break;
				
			case "ARTICLE":// 恢复文章
				json = JSONObject.fromObject(log.getContent());
				Article article = (Article) JSONObject.toBean(json,Article.class);
				checkModuleAndProject(article.getModuleId());
				// key有唯一约束，不置为null会报错
				if( MyString.isEmpty(article.getKey())){
					article.setKey(null);
				}
				articleService.update(article);
				break;
				
			case "MODULE"://恢复模块
				json = JSONObject.fromObject(log.getContent());
				Module module = (Module) JSONObject.toBean(json,Module.class);
				
				// 查看项目是否存在
				if(  MyString.isEmpty( projectService.get(module.getProjectId()).getId() ) ){
					throw new MyException("000049");
				}
				
				// 模块不允许恢复修改操作
				if( !log.getType().equals(LogType.DELTET.name()) ){
					throw new MyException("000050");
				}
				
				moduleService.update(module);
				break;
				
			case "PROJECT"://恢复项目
				json = JSONObject.fromObject(log.getContent());
				Project project = (Project) JSONObject.toBean(json,Project.class);
				projectService.update(project);
				break;
				
			case "SOURCE"://恢复资源
				json = JSONObject.fromObject(log.getContent());
				Source source = (Source) JSONObject.toBean(json,Source.class);
				checkModuleAndProject(source.getModuleId());
				sourceService.update(source);
				break;
		
		}
	}

	private void checkModuleAndProject(String moduleId) throws MyException {
		// 查看模块是否存在
		Module module = moduleService.get(moduleId);
		if( MyString.isEmpty(module.getId()) ){
			throw new MyException("000048");
		}
		
		// 查看项目是否存在
		if(  MyString.isEmpty( projectService.get(module.getProjectId()).getId() ) ){
			throw new MyException("000049");
		}
	}
}
