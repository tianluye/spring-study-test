package com.ztesoft.spring.aware.two;

/**
 * @author tian.lue
 */
public class Market implements AppleAware {

    private Apple apple;

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public String getApple() {
        return apple.getGrand() + "-" + apple.getPrice();
    }

}
