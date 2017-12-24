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
    private static final String CONFIG_FILTER_SUFFIXES = "filterSuffixes";
    private static List<String> FILTER_SUFFIXES;
    private static ThreadLocal<ThreadObject> THREAD_OBJECT = new ThreadLocal<ThreadObject>();

    /**
     * @param filterConfig web.xml 中filter的配置信息
     * @throws ServletException
     */
    @Override
    final public void init(FilterConfig filterConfig) throws ServletException {
        this.FILTER_SUFFIXES = initFilterSuffix(filterConfig);
        log.info("ThreadContext:初始化..........");

    }

    @Override
    public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isFilterSuffix(request)) {
            THREAD_OBJECT.set(new ThreadObject((HttpServletRequest) request, (HttpServletResponse) response));
            chain.doFilter(request, response);
            THREAD_OBJECT.set(null);
            return;
        }

        chain.doFilter(request, response);
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
    protected List<String> initFilterSuffix(FilterConfig filterConfig) {
        String filterSuffixStr = filterConfig.getInitParameter(CONFIG_FILTER_SUFFIXES);
        if (filterSuffixStr == null || StringUtils.isBlank(filterSuffixStr)) {
            return new ArrayList<String>(0);
        }
        if (filterSuffixStr.trim().length() == 0) {
            return new ArrayList<String>(0);
        }

        String[] filterSuffixes = filterSuffixStr.split(SEPARATOR_COMMA);
        for (int i = 0; i < filterSuffixes.length; i++) {
            filterSuffixes[i] = filterSuffixes[i].trim();
        }
        return Arrays.asList(filterSuffixes);
    }

    protected boolean isFilterSuffix(ServletRequest request) {
        if (request instanceof HttpServletRequest) {
            String uri = ((HttpServletRequest) request).getRequestURI();
            if (uri == null) {
                return false;
            }
            if (CollectionUtils.isEmpty(FILTER_SUFFIXES)) {
                return false;
            }

            for (String filterSuffix : FILTER_SUFFIXES) {
                if (uri.toLowerCase().endsWith(filterSuffix.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static HttpServletRequest request() {
        return THREAD_OBJECT.get().request;
    }

    public static HttpServletResponse response() {
        return THREAD_OBJECT.get().response;
    }

}
