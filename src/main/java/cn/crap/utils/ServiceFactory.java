package cn.crap.utils;

import cn.crap.service.ProjectUserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Ehsan
 * @date 2019/2/5 21:05
 */
@Service
public class ServiceFactory implements InitializingBean {

    private static ServiceFactory instance = null;
    @Resource
    private ProjectUserService projectUserService;

    public static ServiceFactory getInstance() {
        return instance;
    }

    @Override
    public void afterPropertiesSet() {
        instance = this;
    }

    public ProjectUserService getProjectUserService() {
        return projectUserService;
    }
}
