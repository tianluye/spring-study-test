package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
        // 如果在这里新增消息转换器，则会覆盖了 Spring默认的消息转换器
    }

    @Autowired
    private SelfMessageConverter converter;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter);
    }

    @Autowired
    private SelfValidator validator;

    @Override
    public Validator getValidator() {
        // 校验器
        return validator;
    }

    // 专门用来配置内容裁决的一些参数
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        /*
        // 是否通过请求Url的扩展名来决定media type
        configurer.favorPathExtension(true)
            // 不检查Accept请求头
            .ignoreAcceptHeader(true)
            .parameterName("mediaType")
            // 设置默认的media type
            .defaultContentType(MediaType.TEXT_HTML)
            // 请求以.html结尾的会被当成MediaType.TEXT_HTML
            .mediaType("html", MediaType.TEXT_HTML)
            // 请求以.json结尾的会被当成MediaType.APPLICATION_JSON
            .mediaType("json", MediaType.APPLICATION_JSON);
        */
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // Configure asynchronous request handling options.
        super.configureAsyncSupport(configurer);
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

    @Autowired
    private MyHandlerMethodArgumentResolver handlerMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        // 增加参数解析器
        argumentResolvers.add(handlerMethodArgumentResolver);
    }

    @Autowired
    private HandlerMethodReturnValueHandler handlerMethodReturnValueHandler;

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
        returnValueHandlers.add(handlerMethodReturnValueHandler);
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        // 一旦此处重写，那么将不会把默认的三个异常处理器加入列表中
        MyHandlerExceptionResolver myHandlerExceptionResolver = new MyHandlerExceptionResolver();
        exceptionResolvers.add(myHandlerExceptionResolver);
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        // 不要重写此方法，就算重写了，也不会生效。
        // DelegatingWebMvcConfiguration类里没有重写 WebMvcConfigurationSupport.extendHandlerExceptionResolvers()方法
        // 导致在 WebMvcConfigurationSupport类中实例化时调用不到此处。
        // 若一定要重写该方法，可以考虑自定义注解，替代 @EnableWebMvc里面的 @Import({DelegatingWebMvcConfiguration.class})
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
//        registry.addViewController("/contentNegotiation").setViewName("contentNegotiation");
        registry.addViewController("/methodArgument").setViewName("methodArgument");
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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
    }

}