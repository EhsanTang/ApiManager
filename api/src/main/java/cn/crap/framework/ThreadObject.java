package cn.crap.framework;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadObject {
    
    public final HttpServletRequest request;
    public final HttpServletResponse response;

    public ThreadObject(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
}
