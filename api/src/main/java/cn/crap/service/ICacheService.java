package cn.crap.service;

import java.util.List;

import cn.crap.dto.SettingDto;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.model.mybatis.Setting;
import cn.crap.model.User;

public interface ICacheService{

	boolean setStr(String key, String value, int expireTime);
	String getStr(String key);
	boolean delObj(String key);

	SettingDto getSetting(String key);

	List<SettingDto> getSetting();

	String getModuleName(String moduleId);

	Module getModule(String moduleId);

	boolean delObj(String key, String field);

	Object getObj(String key);

	Object setObj(String key, Object value, int expireTime);
	Object setObj(String key, String field, Object value, int expireTime);
	void delStr(String string);
	
	boolean flushDB();
	Object getObj(String string, String string2);
	Project getProject(String projectId);
	User getUser(String userId);
}
