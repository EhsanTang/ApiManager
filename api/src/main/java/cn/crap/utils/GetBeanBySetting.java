package cn.crap.utils;

import cn.crap.framework.SpringContextHolder;
import cn.crap.inter.dao.ICacheDao;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.ISearchService;
import cn.crap.service.CacheService;
import cn.crap.service.LuceneSearchService;
import cn.crap.service.SolrSearchService;


public class GetBeanBySetting {
	 public static ISearchService getSearchService(){
	    	ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
	        if(cacheService.getSetting(Const.SEARCH_TYPE).getValue().equals("solrSearch")){
	            return SpringContextHolder.getBean("solrSearch",SolrSearchService.class);
	        }else{
	            return SpringContextHolder.getBean("luceneSearch",LuceneSearchService.class);
	        }
	    }
	 public static ICacheDao getCacheDao(){
	        if( Config.getRedisIp().trim().equals("") ){
	            return SpringContextHolder.getBean("memoryCacheDao",ICacheDao.class);
	        }else{
	            return SpringContextHolder.getBean("redisCacheDao",ICacheDao.class);
	        }
	    }
}
