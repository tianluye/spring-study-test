package com.ztesoft.spring.webmvc.configureradapter;

import java.io.Serializable;

/**
 * @author tian.lue
 */
public class Car implements Serializable {
    private String name;
    private String brand;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public Car() {
    }
    public Car(String name, String brand) {
        this.name = name;
        this.brand = brand;
    }
}
