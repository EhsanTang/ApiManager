package cn.crap.config;

import cn.crap.framework.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author James
 */
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    private static String[] excludePathPatterns = {
            "/**/*.jpg",
            "/**/*.png",
            "/**/*.html",
            "/**/*.js",
            "/**/*.css",
            "/**/*.jpeg",
            "/**/*.doc",
            "/**/*.pdf",
            "/**/*.ignore"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
    }
}
