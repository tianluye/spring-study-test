package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

/**
 * @Configuration : 标识了该类是一个配置类
 * @EnableWebMvc : 开启 web mvc
 * @ComponentScan : 扫描该类路径下的所有的配置类
 * @author tian.lue
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class ServletConfig extends WebMvcConfigurerAdapter {

    /**
     * 类型转换器 Object <--> String
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addFormatter(new CarFormat("^(\\w+)--(\\d+)$"));
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        SelfMessageConverter converter = new SelfMessageConverter();
        converters.add(converter);
    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
        // (主要针对于路径参数)
        // 匹配结尾 / :会识别 url 的最后一个字符是否为 /
        // true时, localhost:8080/test 与 localhost:8080/test/ 等价
        configurer.setUseTrailingSlashMatch(false);
        // 匹配后缀名：会识别 xx.* 后缀的内容
        // true时, localhost:8080/test 与 localhost:8080/test.tt 得到的路径参数一致，均为 test
        configurer.setUseSuffixPatternMatch(false);
        // (主要针对于路径匹配)
        // 在匹配模式时是否使用后缀模式匹配，默认值为 false
        // false时 /login或 /login.do等都可以进行访问
        // true时，通过 /login.* 就不能访问了
        configurer.setUseRegisteredSuffixPatternMatch(true);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        // 设置其优先级, 其数值越小, 优先级越高
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/convert").setViewName("convert");
        registry.addViewController("/").setViewName("welcome");
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
        registry.addResourceHandler("/resource/**").addResourceLocations("/WEB-INF/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    }

}