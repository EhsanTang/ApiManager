package cn.crap.framework;

import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class ParamFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			filterChain.doFilter(new HttpServletRequestWrapper(request) {


			@Override
			public String getParameter(String name) {
				// 返回值之前 先进行过滤
				return filterInvalidString(super.getParameter(name));
			}


			@Override
			public String[] getParameterValues(String name) {
				// 返回值之前 先进行过滤
				String[] values = super.getParameterValues(name);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						values[i] = filterInvalidString(values[i]);
					}
				}
				return values;
			}
		}, response);
	}

	public String filterInvalidString(String value) {
		// TODO 非法攻击字符过滤
		if (value == null) {
			return null;
		}
		if (MyString.isEmpty(value)){
			return null;
		}
		return value;
	}

}
