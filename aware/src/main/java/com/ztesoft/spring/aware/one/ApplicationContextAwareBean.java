package com.ztesoft.spring.aware.one;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

/**
 * @author tian.lue
 */
public class ApplicationContextAwareBean implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 实现 ApplicationContextAware接口必须重写的方法，注入applicationContext上下文
     * @param applicationContext
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Environment environment = applicationContext.getEnvironment();
        boolean flag = environment.containsProperty("ztesoft");
        System.out.println(flag);
        if (flag) {
            String userName = environment.getProperty("ztesoft");
            System.out.println(userName);
        }
        this.applicationContext = applicationContext;
    }

    private String helloWord = "Hello!World!";

    public void setHelloWord(String helloWord) {
        this.helloWord = helloWord;
    }

    public String getHelloWorld() {
        applicationContext.publishEvent(new ApplicationContextEvent("[" + helloWord + "] is getted"));
        return helloWord;
    }

}
