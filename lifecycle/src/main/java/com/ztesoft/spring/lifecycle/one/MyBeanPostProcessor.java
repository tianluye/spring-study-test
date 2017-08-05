package com.ztesoft.spring.lifecycle.one;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author tian.lue
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        super();
        System.out.println("这是BeanPostProcessor实现类构造器！！");
    }

    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
        System.out.println(name + " -- BeanPostProcessor接口的 postProcessBeforeInitialization方法调用");
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
        System.out.println(name + " -- BeanPostProcessor接口的 postProcessAfterInitialization方法调用");
        return bean;
    }

}
