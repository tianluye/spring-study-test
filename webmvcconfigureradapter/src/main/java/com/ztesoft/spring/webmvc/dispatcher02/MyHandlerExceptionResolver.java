package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tian.lue
 */
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
        Object object, Exception ex) {
        if (ex instanceof NullPointerException) {
            return new ModelAndView("error");
        }
        return null;
    }

}
