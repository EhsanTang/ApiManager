package cn.crap.service.imp.tool;

import java.util.List;

import javax.annotation.Resource;

import cn.crap.adapter.SettingAdapter;
import cn.crap.dto.SettingDto;
import cn.crap.model.mybatis.SettingCriteria;
import cn.crap.model.mybatis.User;
import cn.crap.service.mybatis.imp.MybatisModuleService;
import cn.crap.service.mybatis.imp.MybatisProjectService;
import cn.crap.service.mybatis.imp.MybatisSettingService;
import cn.crap.service.mybatis.imp.MybatisUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.dao.ICacheDao;
import cn.crap.service.ICacheService;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.Project;
import cn.crap.model.mybatis.User;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;

@Service
@Repository(value = "cacheService")
public class CacheService implements ICacheService {
	@Autowired
	private MybatisModuleService moduleService;
	@Autowired
	private Config config;
	@Autowired
	private MybatisSettingService mybatisSettingService;
	
	@Resource(name="memoryCacheDao")
	private ICacheDao memoryCacheDao;
	@Resource(name="redisCacheDao")
	private ICacheDao redisCacheDao;
	@Autowired
	private MybatisProjectService projectService;
	@Autowired
	private MybatisUserService userService;
	
	
	
	
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
	public SettingDto getSetting(String key){
		Object obj = getDao().getObj(Const.CACHE_AUTHORIZE , key);
		
		if(obj == null){
			SettingCriteria example = new SettingCriteria();
			SettingCriteria.Criteria criteria = example.createCriteria();
			criteria.andMkeyEqualTo(key);

			List<cn.crap.model.mybatis.Setting> settings= mybatisSettingService.selectByExample(example);
			if(settings.size() > 0){
				getDao().setObj(Const.CACHE_SETTING, key, SettingAdapter.getDto(settings.get(0)), config.getCacheTime());
				return SettingAdapter.getDto(settings.get(0));
			}
		}else{
			return (SettingDto) obj;
		}
		return new SettingDto();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<SettingDto> getSetting(){
		Object obj = getDao().getObj(Const.CACHE_SETTINGLIST);
		
		if(obj == null){
			List<cn.crap.model.mybatis.Setting> settings= mybatisSettingService.selectByExample(null);
			getDao().setObj(Const.CACHE_SETTINGLIST, SettingAdapter.getDto(settings), config.getCacheTime());
			return SettingAdapter.getDto(settings);
		}else{
			return (List<SettingDto>) obj;
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
			Module module = moduleService.selectByPrimaryKey(moduleId);
			if(module == null)
				module = new Module();
			getDao().setObj(Const.CACHE_MODULE + moduleId, module, config.getCacheTime());
			return module;
				
		}
		return (Module) obj;
	}
	
	public Project getProject(String projectId){
		if(MyString.isEmpty(projectId)){
			return new Project();
		}
		
		Project project = (Project) getDao().getObj(Const.CACHE_PROJECT + projectId);
		if(project == null){
			project = projectService.selectByPrimaryKey(projectId);
			if(project == null)
				project = new Project();
			getDao().setObj(Const.CACHE_PROJECT + projectId, project, config.getCacheTime());
				
		}
 		//内存缓存时拷贝对象，防止在Controller中将密码修改为空时导致问题
		Project p = new Project();
		BeanUtils.copyProperties(project, p);
		return p;
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
			User user = userService.selectByPrimaryKey(userId);
			if(user == null)
				user = new User();
			getDao().setObj(Const.CACHE_USER_MODEL + userId, user, config.getCacheTime());
			return user;
		}
		return (User) obj;
	}
}
