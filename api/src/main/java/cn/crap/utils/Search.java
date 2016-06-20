package cn.crap.utils;

import cn.crap.framework.SpringContextHolder;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.ISearchService;
import cn.crap.service.CacheService;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by yan on 16/6/19.
 */
public class Search implements ApplicationContextAware{
    private static ApplicationContext applicationContext;
    static Logger logger = Logger.getLogger(Search.class);
    public static ISearchService getSearchService(){
    	ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
        logger.debug("search type:" + cacheService.getSetting(Const.SEARCH_TYPE).getValue());
        if(cacheService.getSetting(Const.SEARCH_TYPE).getValue().equals("solrSearch")){
            return (ISearchService) applicationContext.getBean("solrSearch");
        }else{
            return (ISearchService) applicationContext.getBean("luceneSearch");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	Search.applicationContext = applicationContext;

    }
}
