package com.ztesoft.spring.aware.one;

import org.springframework.context.ApplicationListener;

/**
 * @author tian.lue
 */
public class ApplicationContextListener implements ApplicationListener<ApplicationContextEvent> {

    public void onApplicationEvent(ApplicationContextEvent event) {
        System.out.println(event.getSource().toString());
    }

}
