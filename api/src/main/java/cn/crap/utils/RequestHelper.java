package cn.crap.utils;

import cn.crap.framework.ThreadContext;

import java.util.Enumeration;
import java.util.HashMap;

/**
 * @author Ehsan
 * @date 17/12/30 01:23
 */
public class RequestHelper {

    /**
     * @return
     */
    public static HashMap<String, String> getRequestHeaders() {
        HashMap<String, String> requestHeaders = new HashMap<>();
        Enumeration<String> headerNames = ThreadContext.request().getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = ThreadContext.request().getHeader(headerName);
            requestHeaders.put(headerName, headerValue);
        }
        return requestHeaders;
    }

    /**
     *
     * @return
     */
    public static HashMap<String, String> getRequestParams() {
        HashMap<String, String> requestParams = new HashMap<>();
        Enumeration<String> paramNames = ThreadContext.request().getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = ThreadContext.request().getParameter(paramName);
            requestParams.put(paramName, paramValue);
        }
        return requestParams;
    }
}
