package com.ztesoft.spring.upload.config;

import com.ztesoft.spring.upload.interceptor.SelfInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @Configuration : 标识了该类是一个配置类
 * @EnableWebMvc : 开启 web mvc
 * @ComponentScan : 扫描该类路径下的所有的配置类
 * @author tian.lue
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.ztesoft.spring.upload"})
public class UploadServletConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        // 设置其优先级, 其数值越小, 优先级越高
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/").setViewName("home");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        registry.jsp("/WEB-INF/view/", ".html");
        registry.enableContentNegotiation(new MappingJackson2JsonView());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 此方法用来专门注册一个 Handler，来处理静态资源的，例如：图片，js，css等
        super.addResourceHandlers(registry);
        // http://localhost:8080/dispatcher02/resource/js/jquery.js --> WEB-INF/js/jquery.js
        registry.addResourceHandler("/resource/**").addResourceLocations("/WEB-INF/");
    }

    /**
     * 注册一个默认的Handler：DefaultServletHttpRequestHandler
     * 这个Handler也是用来处理静态文件的，它会尝试映射/*。
     * 当DispatcherServlet映射/时（/ 和/* 是有区别的），并且没有找到合适的Handler来处理请求时，
     * 就会交给DefaultServletHttpRequestHandler 来处理。
     * 注意：这里的静态资源是放置在web根目录下，而非 WEB-INF 下。
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

//    @Autowired
//    private LocaleChangeInterceptor localeChangeInterceptor;
    @Autowired
    private SelfInterceptor selfInterceptor;

    /**
     * 多个拦截器组成一个拦截器链
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(selfInterceptor).addPathPatterns("/locale");
        super.addInterceptors(registry);
    }

}
