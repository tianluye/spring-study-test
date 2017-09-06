package com.ztesoft.spring.lifecycle.two;

import org.springframework.stereotype.*;

/**
 * @author tian.lue
 */
@org.springframework.stereotype.Service("car")
public class Car {

    public String name = null;

    public void initName(Long id) {
        String _name = null == id ? "qqq" : "aaa";
        name = _name;
    }

}
