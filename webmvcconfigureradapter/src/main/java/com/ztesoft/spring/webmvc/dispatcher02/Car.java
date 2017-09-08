package com.ztesoft.spring.webmvc.dispatcher02;

import java.io.Serializable;

/**
 * @author tian.lue
 */
public class Car implements Serializable {

    private String brand;

    private String price;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Car() {
    }
}
