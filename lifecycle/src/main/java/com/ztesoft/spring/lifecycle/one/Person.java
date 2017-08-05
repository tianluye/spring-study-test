package com.ztesoft.spring.lifecycle.one;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author tian.lue
 */
public class Person implements BeanNameAware, BeanFactoryAware, ApplicationContextAware,
    InitializingBean, DisposableBean {

    /**
     *  私有属性
     */
    private String name;
    private String sex;
    private String phone;

    /**
     * 用于接收 Aware接口的 setXxx方法的值
     */
    private String beanName;
    private BeanFactory beanFactory;
    private ApplicationContext applicationContext;

    /**
     * 必须有的默认构造器
     */
    public Person() {
        System.out.println("开始执行 Person的默认构造器。。。");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("开始执行 Person的 setName方法。。。");
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        System.out.println("开始执行 Person的 setSex方法。。。");
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        System.out.println("开始执行 Person的 setPhone方法。。。");
    }

    @Override public String toString() {
        return "Person{" + "name='" + name + '\'' + ", sex='" + sex + '\'' + ", phone='" + phone + '\'' + '}';
    }

    // 这是BeanFactoryAware接口方法
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        System.out.println("开始执行 BeanFactoryAware的 setBeanFactory方法。。。");
    }

    // 这是BeanNameAware接口方法
    public void setBeanName(String beanName) {
        this.beanName = beanName;
        System.out.println("开始执行 BeanNameAware的 setBeanName方法。。。");
    }

    // 这是ApplicationContextAware接口方法
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        System.out.println("开始执行 ApplicationContextAware的 setApplicationContext方法。。。");
    }

    // 这是InitializingBean接口方法
    public void afterPropertiesSet() throws Exception {
        System.out.println("【InitializingBean接口】调用InitializingBean.afterPropertiesSet()");
    }

    // 这是DisposableBean接口方法
    public void destroy() throws Exception {
        System.out.println("【DiposibleBean接口】调用 DisposableBean.destory()");
    }

    // 通过<bean>的init-method属性指定的初始化方法
    public void myInit() {
        System.out.println("【init-method】调用<bean>的init-method属性指定的初始化方法");
    }

    // 通过<bean>的destroy-method属性指定的初始化方法
    public void myDestroy() {
        System.out.println("【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
    }

}