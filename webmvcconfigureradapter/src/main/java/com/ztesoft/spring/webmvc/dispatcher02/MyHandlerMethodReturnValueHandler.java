package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tian.lue
 * 为什么要实现 BeanPostProcessor？
 * 在 Spring中，RequestMappingHandlerAdapter管理处理返回值的handler，且是有默认的优先级顺序的。
 * 自定义的处理返回值的 handler默认是最后加载，只有当 Spring提供的 handler不足以解析的时候，才会去尝试使用自定义的 handler。
 * 这里实现这个接口，可以获取到RequestMappingHandlerAdapter类的 bean实例，然后获取 handler列表，改变其加载的顺序，这样就可以使用自定义的handler。
 */
public class MyHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler, BeanPostProcessor {

    /**
     * 检验是否支持本处理器处理，返回true会执行handleReturnValue
     * @param returnType
     * @return
     */
    public boolean supportsReturnType(MethodParameter returnType) {
        if (returnType.getMethodAnnotation(MyResponseBody.class) != null) {
            return true;
        }
        return false;
    }

    /**
     * 处理返回值的方法，returnValue即是controller方法中的返回值
     * @param returnValue
     * @param returnType
     * @param mavContainer
     * @param nbRequest
     * @throws Exception
     */
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
        NativeWebRequest nbRequest) throws Exception {
        // 设置这个就是最终的处理类了，处理完不再去找下一个类进行处理
        mavContainer.setRequestHandled(true);
        HttpServletResponse response = nbRequest.getNativeResponse(HttpServletResponse.class);
        response.setContentType("text/plain;charset=utf-8");
        Student student = (Student) returnValue;
        // 获取方法上的注解
        StudentPrefix prefix = returnType.getMethodAnnotation(StudentPrefix.class);
        response.getWriter().write(prefix.value() + " : " + student.getName());
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 判断 bean是否是 RequestMappingHandlerAdapter实例
        if (bean instanceof RequestMappingHandlerAdapter) {
            // 获取所有的处理返回值的 handler列表
            List<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>(
                ((RequestMappingHandlerAdapter) bean).getReturnValueHandlers());
            // 获取自定义的 handler
            MyHandlerMethodReturnValueHandler myHandlerMethodReturnValueHandler = null;
            for (int i = 0; i < handlers.size(); i++) {
                HandlerMethodReturnValueHandler handler = handlers.get(i);
                if (handler instanceof MyHandlerMethodReturnValueHandler) {
                    myHandlerMethodReturnValueHandler = (MyHandlerMethodReturnValueHandler) handler;
                    break;
                }
            }
            // 将自定义的 handler放在首位
            if (myHandlerMethodReturnValueHandler != null) {
                handlers.remove(myHandlerMethodReturnValueHandler);
                handlers.add(0, myHandlerMethodReturnValueHandler);
                ((RequestMappingHandlerAdapter) bean).setReturnValueHandlers(handlers); // change the jsonhandler sort
            }
        }
        return bean;
    }

    public MyHandlerMethodReturnValueHandler() {
        super();
    }
}
