package com.ztesoft.spring.aware.two;

import java.io.Serializable;

/**
 * @author tian.lue
 */
public class Apple implements Serializable {

    private String grand;

    private double price;

    public String getGrand() {
        return grand;
    }

    public void setGrand(String grand) {
        this.grand = grand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
