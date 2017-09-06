package com.ztesoft.spring.lifecycle.two;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author tian.lue
 */
public class TwoMain {

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-lifecycle-two.xml");
        Service service = context.getBean(Service.class);
        service.getCarName();
    }

}
