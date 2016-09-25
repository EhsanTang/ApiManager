package cn.crap.utils;

import cn.crap.framework.SpringContextHolder;
import cn.crap.inter.dao.ICacheDao;


public class GetBeanBySetting {
	 public static ICacheDao getCacheDao(){
	        if( Config.getRedisIp().trim().equals("") ){
	            return SpringContextHolder.getBean("memoryCacheDao",ICacheDao.class);
	        }else{
	            return SpringContextHolder.getBean("redisCacheDao",ICacheDao.class);
	        }
	    }
}
