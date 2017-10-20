package com.ztesoft.spring.upload.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author tian.lue
 */
public class SelfInterceptor extends HandlerInterceptorAdapter {

    public SelfInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws UnsupportedEncodingException {
        if ("POST".equals(request.getMethod())) {
            System.out.println("------ Update System Language ------");
            return true;
        }
        return true;
    }

}
