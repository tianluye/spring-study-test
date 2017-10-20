package com.ztesoft.spring.upload.config;

import com.ztesoft.spring.upload.common.StaticBean;
import com.ztesoft.spring.upload.interceptor.SelfInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @author tian.lue
 */
@Configuration
public class SelfBeanConfig implements BeanPostProcessor {

    public SelfBeanConfig() {
    }

    @Bean(name = "resourceBundleMessageSource")
    public ResourceBundleMessageSource getResourceBundleMessageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        // 设置资源文件的路径 (一定要将其设置为 Resource Bundle)
        // spring.messages.basename = i18n/messages
        resourceBundleMessageSource.setBasename("i18n/message");
        // 设置编码，否则中文会乱码
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return resourceBundleMessageSource;
    }

    @Bean(name = "selfInterceptor")
    public SelfInterceptor getSelfInterceptor() {
        return new SelfInterceptor();
    }

    public Object postProcessBeforeInitialization(Object var1, String var2) throws BeansException {
        return var1;
    }

    public Object postProcessAfterInitialization(Object var1, String var2) throws BeansException {
        if (var1 instanceof ResourceBundleMessageSource) {
            StaticBean.resourceBundleMessageSource = (ResourceBundleMessageSource) var1;
        }
        return var1;
    }

}
