package com.ztesoft.spring.aware.one;

import org.springframework.context.ApplicationEvent;

/**
 * @author tian.lue
 */
public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

}
