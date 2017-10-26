package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletRegistration;

/**
 * @author tian.lue
 */
public class DispatcherAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    public Class<?>[] getServletConfigClasses() {
        return new Class[] {ServletConfig.class};
    }

    /**
     * 设置 DispatcherServlet的路由
     * 因为存在多个 DispatcherServlet，这里的路由设置必须加上 '*'，否则或默认走 '/'路由。
     * @return
     */
    @Override
    public String[] getServletMappings() {
        return new String[] {"/dispatcher02/*"};
    }

    /**
     * 重写 customizeRegistration方法，设置该 DispatcherServlet的加载顺序
     * @param registration
     */
    @Override
    public void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setLoadOnStartup(2);
        registration.setAsyncSupported(true);
    }

    /**
     * 重写 getServletName方法，设置 DispatcherServlet的名称
     * @return
     */
    @Override
    public String getServletName() {
        return "dispatcher";
    }

}
