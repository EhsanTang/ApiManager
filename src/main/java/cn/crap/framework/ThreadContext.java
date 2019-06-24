package cn.crap.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class ThreadContext implements Filter, WebMvcConfigurer {

    private static final String SEPARATOR_COMMA = ",";
    private static final String CONFIG_IGNORE_SUFFIXES = ".jpg,.png,.html,.js,.css,.jpeg,.doc,.pdf,.ignore";
    private static List<String> IGNORE_SUFFIXES;
    private static ThreadLocal<ThreadObject> THREAD_OBJECT = new ThreadLocal<>();

    public static HttpServletRequest request() {
        if (THREAD_OBJECT.get() == null) {
            return null;
        }
        return THREAD_OBJECT.get().request;
    }

    public static void set(HttpServletRequest request, HttpServletResponse response) {
        THREAD_OBJECT.set(new ThreadObject(request, response));
    }

    public static void clear() {
        THREAD_OBJECT.set(null);
    }

    public static HttpServletResponse response() {
        if (THREAD_OBJECT.get() == null) {
            return null;
        }
        return THREAD_OBJECT.get().response;
    }

    /**
     * @param filterConfig web.xml 中filter的配置信息
     * @throws ServletException
     */
    @Override
    final public void init(FilterConfig filterConfig) {
        IGNORE_SUFFIXES = initIgnoreSuffix();
        System.out.println("ThreadContext:初始化..........");

    }

    @Override
    public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isIgnoreSuffix(request)) {
            chain.doFilter(request, response);
            return;
        }

        THREAD_OBJECT.set(new ThreadObject(request, response));
        chain.doFilter(request, response);
        THREAD_OBJECT.set(null);
        return;

    }

    @Override
    public void destroy() {

    }

    /**
     * 初始化用逗号分隔的路径参数
     *
     * @return
     */
    protected List<String> initIgnoreSuffix() {
        String[] filterSuffixes = CONFIG_IGNORE_SUFFIXES.split(SEPARATOR_COMMA);
        for (int i = 0; i < filterSuffixes.length; i++) {
            filterSuffixes[i] = filterSuffixes[i].trim();
        }
        return Arrays.asList(filterSuffixes);
    }

    /**
     * 判断地址是否是需要忽略的
     *
     * @param request
     * @return
     */
    protected boolean isIgnoreSuffix(ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            String uri = ((HttpServletRequest) request).getRequestURI();
            if (uri == null) {
                return true;
            }
            if (CollectionUtils.isEmpty(IGNORE_SUFFIXES)) {
                return true;
            }

            for (String ignoreSuffix : IGNORE_SUFFIXES) {
                if (uri.toLowerCase().endsWith(ignoreSuffix.toLowerCase())) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/home.do");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/");
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

}