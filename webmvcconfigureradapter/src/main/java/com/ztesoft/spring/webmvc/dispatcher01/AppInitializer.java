package com.ztesoft.spring.webmvc.dispatcher01;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author tian.lue
 */
public class AppInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext webApplicationContext =
            new AnnotationConfigWebApplicationContext();
        // 在应用上下文中注册一个配置类，启动 Spring MVC
        webApplicationContext.register(AppConfig.class);
        // 配置 DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);
        ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcherServlet", dispatcherServlet);
        /**
         配置 DispatcherServlet的 mapping
         <servlet-mapping>
         <servlet-name>springmvc</servlet-name>
         <url-pattern>/</url-pattern>
         </servlet-mapping>
         */
        dynamic.addMapping("/dispatcher01/*");
    }
}
