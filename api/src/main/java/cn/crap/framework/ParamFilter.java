package cn.crap.framework;

import cn.crap.utils.MyString;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤无效参数内容
 */
public class ParamFilter extends OncePerRequestFilter {
    private static String IGNORE_PARAM_PRE = "crShow";


    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
    	    try {
                filterChain.doFilter(new HttpServletRequestWrapper(request) {
                    @Override
                    public String getParameter(String name) {
                        if (name.trim().startsWith(IGNORE_PARAM_PRE)) {
                            return null;
                        }

                        // 返回值之前 先进行过滤
                        return filterInvalidString(super.getParameter(name));
                    }


                    @Override
                    public String[] getParameterValues(String name) {
                        if (name.trim().startsWith(IGNORE_PARAM_PRE)) {
                            return null;
                        }

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
            }catch (Exception e){
    	        e.printStackTrace();
            }
	}

	public String filterInvalidString(String value) {
		if (value == null) {
			return null;
		}
		if (MyString.isEmpty(value)){
			return null;
		}
		return value;
	}

}
