package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

/**
 * @author tian.lue
 */
@Configuration
public class BeanConfiguration {

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
    public HandlerMethodArgumentResolver getHandlerMethodArgumentResolver() {
        return new MyHandlerMethodArgumentResolver();
    }

}
