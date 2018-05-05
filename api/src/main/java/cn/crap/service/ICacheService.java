package cn.crap.service;

import java.util.List;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.SettingDto;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.Project;
import cn.crap.model.mybatis.Setting;
import cn.crap.model.mybatis.User;

public interface ICacheService<T>{
	T get(String key);
	boolean del(String key);

	boolean flushDB();
}
