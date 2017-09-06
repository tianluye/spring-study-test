package com.ztesoft.spring.webmvc.configureradapter;

import java.io.Serializable;

/**
 * @author tian.lue
 */
public class User implements Serializable {
    private Long id;
    private String name;
    private Car car;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Car getCar() {
        return car;
    }
    public void setCar(Car car) {
        this.car = car;
    }
}
