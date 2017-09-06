package com.ztesoft.spring.lifecycle.one;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tian.lue
 */
public class LifeCycleMain {

    public static void main(String[] args) {
        AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-lifecycle-one.xml");
        Person person = applicationContext.getBean(Person.class);
        System.out.println(person);
        applicationContext.close();
    }

}
