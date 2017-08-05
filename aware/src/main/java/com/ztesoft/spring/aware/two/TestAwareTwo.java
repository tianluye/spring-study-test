package com.ztesoft.spring.aware.two;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author tian.lue
 */
public class TestAwareTwo {

    public static void main(String[] args) {
        /**
         * 此方法已经过期，使用 DefaultListableBeanFactory(ConfigurableBeanFactory的实现类)和 XmlBeanDefinitionReader代替
         * ConfigurableBeanFactory factory = new XmlBeanFactory(new ClassPathResource("spring-aware.xml"));
         */
        Resource resource = new ClassPathResource("spring-aware.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(resource);

        Apple apple = factory.getBean(Apple.class);
        BeanPostProcessor bpp = new AppleAwarePostProcessor(apple);

        // 工厂对象中加入自定义的BeanPostProcessor
        factory.addBeanPostProcessor(bpp);

        Market market = factory.getBean(Market.class);

        System.out.println(market.getApple());
    }

}
