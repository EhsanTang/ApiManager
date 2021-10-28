package cn.crap.utils;

import cn.crap.service.ProjectUserService;
import cn.crap.service.tool.ProjectCache;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * @author Ehsan
 * @date 2019/2/5 21:05
 */
@Service
public class ServiceFactory implements InitializingBean {

    @Resource
    @Getter
    private ProjectUserService projectUserService;

    @Resource
    @Getter
    private ProjectCache projectCache;


    /************* 以下方法不要改动 **********/
    private static ServiceFactory instance = null;
    public static ServiceFactory getInstance(){
        return instance;
    }

    @Override
    public void afterPropertiesSet(){
        instance = this;
    }

}
