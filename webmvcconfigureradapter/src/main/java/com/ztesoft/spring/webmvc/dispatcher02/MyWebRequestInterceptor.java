package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * @author tian.lue
 */
public class MyWebRequestInterceptor implements WebRequestInterceptor {

    /**
     * 在请求处理之前执行，该方法主要是用于准备资源数据的，然后可以把它们当做请求属性放到WebRequest中
     */
    public void preHandle(WebRequest request) throws Exception {
        System.out.println("实现 'WebRequestInterceptor'的拦截器在请求处理之前进行调用（Controller方法调用之前）");
        //这个是放到request范围内的，所以只能在当前请求中的request中获取到
        request.setAttribute("request", "request", WebRequest.SCOPE_REQUEST);
        //这个是放到session范围内的，如果环境允许的话它只能在局部的隔离的会话中访问，否则就是在普通的当前会话中可以访问
        request.setAttribute("session", "session", WebRequest.SCOPE_SESSION);
        //如果环境允许的话，它能在全局共享的会话中访问，否则就是在普通的当前会话中访问
        request.setAttribute("globalSession", "globalSession", WebRequest.SCOPE_GLOBAL_SESSION);
    }

    /**
     * 该方法将在Controller执行之后，返回视图之前执行，ModelMap表示请求Controller处理之后返回的Model对象，所以可以在
     * 这个方法中修改ModelMap的属性，从而达到改变返回的模型的效果。
     */
    public void postHandle(WebRequest request, ModelMap map) throws Exception {
        System.out.println("实现 'WebRequestInterceptor'的拦截器在请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
        for (String key:map.keySet()) {
            System.out.println(key + "-------------------------");;
        }
        map.put("name3", "value3");
        map.put("name1", "value1");
    }

    /**
     * 该方法将在整个请求完成之后，也就是说在视图渲染之后进行调用，主要用于进行一些资源的释放
     */
    public void afterCompletion(WebRequest var1, Exception var2) throws Exception {
        System.out.println("实现 'WebRequestInterceptor'的拦截器在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }

}
