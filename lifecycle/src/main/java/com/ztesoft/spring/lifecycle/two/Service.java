package com.ztesoft.spring.lifecycle.two;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tian.lue
 */
@Component("service")
public class Service {

    @Autowired
    private Car car;

    public void getCarName() {
        car.initName(1L);
        String carName = car.name;
        System.out.println(carName);
    }

}
