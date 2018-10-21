package cn.crap.framework;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadContext implements Filter {

    protected Logger log = Logger.getLogger(getClass());

    private static final String SEPARATOR_COMMA = ",";
    private static final String CONFIG_IGNORE_SUFFIXES = "ignoreSuffixes";
    private static List<String> IGNORE_SUFFIXES;
    private static ThreadLocal<ThreadObject> THREAD_OBJECT = new ThreadLocal<ThreadObject>();

    /**
     * @param filterConfig web.xml 中filter的配置信息
     * @throws ServletException
     */
    @Override
    final public void init(FilterConfig filterConfig) throws ServletException {
        this.IGNORE_SUFFIXES = initIgnoreSuffix(filterConfig);
        log.info("ThreadContext:初始化..........");

    }

    @Override
    public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isIgnoreSuffix(request)) {
            chain.doFilter(request, response);
            return;
        }

        THREAD_OBJECT.set(new ThreadObject((HttpServletRequest) request, (HttpServletResponse) response));
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
    protected List<String> initIgnoreSuffix(FilterConfig filterConfig) {
        String ignoreSuffixStr = filterConfig.getInitParameter(CONFIG_IGNORE_SUFFIXES);
        if (ignoreSuffixStr == null || StringUtils.isBlank(ignoreSuffixStr)) {
            return new ArrayList<String>(0);
        }
        if (ignoreSuffixStr.trim().length() == 0) {
            return new ArrayList<String>(0);
        }

        String[] filterSuffixes = ignoreSuffixStr.split(SEPARATOR_COMMA);
        for (int i = 0; i < filterSuffixes.length; i++) {
            filterSuffixes[i] = filterSuffixes[i].trim();
        }
        return Arrays.asList(filterSuffixes);
    }

    /**
     * 判断地址是否是需要忽略的
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

    public static HttpServletRequest request() {
        if (THREAD_OBJECT.get() == null){
            return null;
        }
        return THREAD_OBJECT.get().request;
    }

    public static void set(HttpServletRequest request, HttpServletResponse response) {
        THREAD_OBJECT.set(new ThreadObject(request, response));
    }

    public static void clear(){
        THREAD_OBJECT.set(null);
    }

    public static HttpServletResponse response() {
        if (THREAD_OBJECT.get() == null){
            return null;
        }
        return THREAD_OBJECT.get().response;
    }

}
