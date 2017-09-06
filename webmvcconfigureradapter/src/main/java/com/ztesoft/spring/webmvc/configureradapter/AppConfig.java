package com.ztesoft.spring.webmvc.configureradapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author tian.lue
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class AppConfig {

    @Bean
    public Car car() {
        return new Car("asd", "qwe");
    }

}
