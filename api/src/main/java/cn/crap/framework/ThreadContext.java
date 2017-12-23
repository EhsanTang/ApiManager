package cn.crap.framework;

import cn.crap.utils.MyString;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ThreadContext implements Filter {

	private static final String SEPARATOR_COMMA = ",";
	private static final String IGNORE_SUFFIX = "ignoreSuffix";
	private static List<String> ignoreSuffix;
	private static ThreadLocal<ThreadObject> THREAD_OBJECT = new ThreadLocal<ThreadObject>() {
		protected ThreadObject initialValue() {
			throw new RuntimeException("程序未初始化，请在web.xml中 配置过滤器");
		}
	};

	/**
	 *
	 * @param filterConfig web.xml 中filter的配置信息
	 * @throws ServletException
	 */
	@Override
	final public void init(FilterConfig filterConfig) throws ServletException {
		this.ignoreSuffix = initIgnoreSuffix(filterConfig);

	}

	@Override
	public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;

		if (isIgnoreSuffix(request)){
			chain.doFilter(request, response);
			return;
		}

		THREAD_OBJECT.set(new ThreadObject((HttpServletRequest) request, (HttpServletResponse) response));
		chain.doFilter(request, response);
		THREAD_OBJECT.set(null);
	}

	@Override
	public void destroy() {

	}


	/**
	 * 初始化用逗号分隔的路径参数
	 * @return
	 */
	protected List<String> initIgnoreSuffix(FilterConfig filterConfig){
		String ignoreSuffix = filterConfig.getInitParameter(IGNORE_SUFFIX);
		if(ignoreSuffix == null || StringUtils.isBlank(ignoreSuffix)){
			return new ArrayList<String>(0);
		}
		if (ignoreSuffix.trim().length() == 0){
			return new ArrayList<String>(0);
		}

		String[] exclusion = ignoreSuffix.split(SEPARATOR_COMMA);
		for (int i = 0; i < exclusion.length; i++) {
			exclusion[i] = exclusion[i].trim();
		}
		return Arrays.asList(exclusion);
	}

	protected boolean isIgnoreSuffix(ServletRequest request){
		if (request instanceof HttpServletRequest) {
			String uri = ((HttpServletRequest) request).getRequestURI();
			if(uri == null){
				return true;
			}
			 if (CollectionUtils.isEmpty(ignoreSuffix)){
				return true;
			 }

			 for(String str : ignoreSuffix){
			 	if (uri.toLowerCase().endsWith(str.toLowerCase())){
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
