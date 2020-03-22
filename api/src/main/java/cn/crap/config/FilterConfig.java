package cn.crap.config;

import cn.crap.framework.ParamFilter;
import cn.crap.framework.ThreadContext;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.HashMap;
import java.util.Map;

/**
 * @author James
 */
public class FilterConfig {

    public static Map<String, String> initParameters = new HashMap<String, String>(){
        {
            put("ignoreSuffixes", ".jpg,.png,.html,.js,.css,.jpeg,.doc,.pdf,.ignore");
        }
    };

    @Bean(name = "threadContextFilter")
    public FilterRegistrationBean<ThreadContext> contextFilterRegistrationBean() {
        FilterRegistrationBean<ThreadContext> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ThreadContext());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }

    private static String[] urlPatterns = {
            "*.do",
            "*.json",
            "*.htm"
    };

    @Bean(name = "paramFilter")
    public FilterRegistrationBean<ParamFilter> createParamFilter() {
        FilterRegistrationBean<ParamFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new ParamFilter());
        filterRegistrationBean.addUrlPatterns(urlPatterns);
        return filterRegistrationBean;
    }
}
