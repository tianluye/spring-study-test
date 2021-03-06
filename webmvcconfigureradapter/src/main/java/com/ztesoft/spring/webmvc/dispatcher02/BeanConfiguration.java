package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author tian.lue
 */
@Configuration
public class BeanConfiguration {

    public BeanConfiguration() {
    }

    @Bean(name = {"AoDi"})
    public Car getADCar() {
        Car car = new Car();
        car.setBrand("AoDi");
        car.setPrice("500000");
        return car;
    }

    @Bean(name = {"DaZhong"})
    public Car getDZCar() {
        Car car = new Car();
        car.setBrand("DaZhong");
        car.setPrice("600000");
        return car;
    }

    @Bean(name = "resourceBundleMessageSource")
    public ResourceBundleMessageSource getResourceBundleMessageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        // 设置资源文件的路径 (一定要将其设置为 Resource Bundle)
        // spring.messages.basename = i18n/messages
        resourceBundleMessageSource.setBasename("i18n/messages");
        // 设置编码，否则中文会乱码
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return resourceBundleMessageSource;
    }

    @Bean(name = "handlerMethodArgumentResolver")
    public MyHandlerMethodArgumentResolver getHandlerMethodArgumentResolver() {
        return new MyHandlerMethodArgumentResolver();
    }

    @Bean(name = "handlerMethodReturnValueHandler")
    public MyHandlerMethodReturnValueHandler handlerMethodReturnValueHandler() {
        return new MyHandlerMethodReturnValueHandler();
    }

    @Bean(name = "myHandlerExceptionResolver")
    public MyHandlerExceptionResolver handlerExceptionResolver() {
        return new MyHandlerExceptionResolver();
    }

    @Bean(name = "messageCodesResolver")
    public DefaultMessageCodesResolver messageCodesResolver() {
        DefaultMessageCodesResolver messageCodesResolver = new DefaultMessageCodesResolver();
        messageCodesResolver.setPrefix("msg.");
        return messageCodesResolver;
    }

    @Bean(name = "handlerInterceptor")
    public MyHandlerInterceptor myHandlerInterceptor() {
        return new MyHandlerInterceptor();
    }

    @Bean(name = "webRequestInterceptor")
    public MyWebRequestInterceptor myWebRequestInterceptor() {
        return new MyWebRequestInterceptor();
    }

}