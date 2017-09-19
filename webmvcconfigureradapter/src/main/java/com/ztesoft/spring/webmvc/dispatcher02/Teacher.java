package com.ztesoft.spring.webmvc.dispatcher02;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author tian.lue
 */
public class Teacher implements Serializable {

    private String name;
    private int age;
    private Date date;
    private List<String> love;
    private List<Car> car;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getLove() {
        return love;
    }

    public void setLove(List<String> love) {
        this.love = love;
    }

    public List<Car> getCar() {
        return car;
    }

    public void setCar(List<Car> car) {
        this.car = car;
    }

    public Teacher() {
    }
}
