package com.ztesoft.spring.aware.one;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author tian.lue
 */
public class MainAwareTest {

    public static void main(String[] args) throws Exception {
        // 加载 spring的上下文
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-aware.xml");
        // 获取配置的 VM Options参数
        String pro = System.getProperty("spring.config.location");
        // 读取配置文件信息
        InputStream in = new BufferedInputStream(new FileInputStream(pro));
        Properties properties = new Properties();
        properties.load(in);
        properties.list(System.out);
        // 获取 bean
        ApplicationContextAwareBean awareBean = applicationContext.getBean(ApplicationContextAwareBean.class);
        String msg = awareBean.getHelloWorld();
        System.out.println(msg);
    }

    /********* result *************************
     *  true
        TestZteSoft
        -- listing properties --
        ztesoft=ZteSoft
        [Hello Swiftlet!] is getted
        Hello Swiftlet!
     */

}
