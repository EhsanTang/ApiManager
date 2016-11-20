package cn.crap.service.tool;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.inter.dao.ICacheDao;
import cn.crap.inter.dao.IModuleDao;
import cn.crap.inter.dao.IProjectDao;
import cn.crap.inter.dao.ISettingDao;
import cn.crap.inter.dao.IUserDao;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.model.Setting;
import cn.crap.model.User;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Service
@Repository(value = "cacheService")
public class CacheService implements ICacheService {
	@Resource(name="settingDao")
	private ISettingDao settingDao;
	@Resource(name="dataCenterDao")
	private IModuleDao dataCenterDao;
	@Autowired
	private Config config;
	
	@Resource(name="memoryCacheDao")
	private ICacheDao memoryCacheDao;
	@Resource(name="redisCacheDao")
	private ICacheDao redisCacheDao;
	@Resource(name="userDao")
	private IUserDao userDao;
	@Resource(name="projectDao")
	private IProjectDao projectDao;
	
	
	
	
	private ICacheDao getDao(){
		if( config.getRedisIp().trim().equals("") ){
			return memoryCacheDao;
		}else{
			return redisCacheDao;
		}
	}
	
	@Override
	public Object getObj(String key){
		return getDao().getObj(key);
	}
	
	@Override
	public Object setObj(String key, Object value, int expireTime){
		return getDao().setObj(key, value, expireTime);
	}
	
	@Override
	public boolean delObj(String key){
		return getDao().delObj(key);
	}
	
	@Override
	public boolean delObj(String key,String field){
		return getDao().delObj(key,field);
	}
	
	@Override
	@Transactional
	public Setting getSetting(String key){
		Object obj = getDao().getObj(Const.CACHE_AUTHORIZE , key);
		
		if(obj == null){
			List<Setting> settings = settingDao.findByMap(Tools.getMap("key",key), null, null);
			if(settings.size() > 0){
				getDao().setObj(Const.CACHE_SETTING, key, settings.get(0), config.getCacheTime());
				return settings.get(0);
			}
		}else{
			return (Setting) obj;
		}
		return new Setting();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Setting> getSetting(){
		Object obj = getDao().getObj(Const.CACHE_SETTINGLIST);
		
		if(obj == null){
			List<Setting> settings = settingDao.findByMap(null, null, null);
			getDao().setObj(Const.CACHE_SETTINGLIST, settings, config.getCacheTime());
			return settings;
		}else{
			return (List<Setting>) obj;
		}
	}
	
	@Override
	@Transactional
	public Module getModule(String moduleId){
		if(MyString.isEmpty(moduleId)){
			return new Module();
		}
		
		Object obj = getDao().getObj(Const.CACHE_MODULE + moduleId);
		if(obj == null){
			Module module = dataCenterDao.get(moduleId);
			if(module == null)
				module = new Module();
			getDao().setObj(Const.CACHE_MODULE + moduleId, module, config.getCacheTime());
			return module;
				
		}
		return (Module) obj;
	}
	
	@Override
	@Transactional
	public Project getProject(String projectId){
		if(MyString.isEmpty(projectId)){
			return new Project();
		}
		
		Project project = (Project) getDao().getObj(Const.CACHE_PROJECT + projectId);
		if(project == null){
			project = projectDao.get(projectId);
			if(project == null)
				project = new Project();
			getDao().setObj(Const.CACHE_PROJECT + projectId, project, config.getCacheTime());
				
		}
		return project;
// 		内存缓存时拷贝对象，防止在Controller中将密码修改为空时导致问题
//		Project p = new Project();
//		BeanUtils.copyProperties(project, p);
//		return p;
	}
	
	@Override
	public String getModuleName(String moduleId){
		String name = getModule(moduleId).getName();
		if(MyString.isEmpty(name))
			name = "无";
		return name;
	}

	@Override
	public boolean setStr(String key, String value, int expireTime) {
		return getDao().setStr(key, value, expireTime);
	}

	@Override
	public String getStr(String key) {
		return getDao().getStr(key);
	}

	@Override
	public void delStr(String key) {
		getDao().delStr(key);
	}

	@Override
	public boolean flushDB() {
		return getDao().flushDB();
	}

	@Override
	public Object setObj(String key, String field, Object value, int expireTime) {
		return getDao().setObj(key, field, value, expireTime);
	}

	@Override
	public Object getObj(String key, String field) {
		return getDao().getObj(key, field);
	}
	
	@Override
	@Transactional
	public User getUser(String userId){
		if(MyString.isEmpty(userId)){
			return new User();
		}
		
		Object obj = getDao().getObj(Const.CACHE_USER_MODEL + userId);
		if(obj == null){
			User user = userDao.get(userId);
			if(user == null)
				user = new User();
			getDao().setObj(Const.CACHE_USER_MODEL + userId, user, config.getCacheTime());
			return user;
		}
		return (User) obj;
	}
}
