package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tian.lue
 */
@Configuration
public class BeanConfiguration {

    @Bean(name = {"AoDi"})
    public Car getADCar() {
        Car car = new Car();
        car.setBrand("AoDi");
        car.setPrice("500000");
        return car;
    }

    @Bean(name = {"DaZhong"})
    public Car getDZCar() {
        Car car = new Car();
        car.setBrand("DaZhong");
        car.setPrice("600000");
        return car;
    }

}
