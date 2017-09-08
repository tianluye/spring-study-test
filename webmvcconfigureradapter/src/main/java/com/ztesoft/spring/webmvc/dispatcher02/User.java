package com.ztesoft.spring.webmvc.dispatcher02;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author tian.lue
 */
public class User implements Serializable {

    private String userName;

    private Long age;

    private List<Car> carList;

    private Date birthDay;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public User() {
    }
}
