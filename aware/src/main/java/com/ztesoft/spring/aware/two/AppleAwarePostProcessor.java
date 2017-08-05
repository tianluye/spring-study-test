package com.ztesoft.spring.aware.two;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author tian.lue
 */
public class AppleAwarePostProcessor implements BeanPostProcessor {

    private Apple apple;

    public AppleAwarePostProcessor(Apple apple) {
        this.apple = apple;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AppleAware) {
            ((AppleAware) bean).setApple(apple);
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
