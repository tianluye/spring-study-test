package com.ztesoft.spring.upload.exception;

import com.ztesoft.spring.upload.common.LocaleCache;
import com.ztesoft.spring.upload.common.StaticBean;

import java.util.Locale;

/**
 * @author tian.lue
 */
public abstract class ValidateUtil {

    public static void isTrue(boolean flag, String errorCode, String ... args) throws Exception {
        if (flag) {
            return;
        }
        Locale locale = LocaleCache.getLocale();
        /**
         * 参数 1、对应资源文件中的 Key
         * 参数 2、对应资源文件中的 Value里的占位符 {}
         * 参数 3、当资源文件中找不到参数 1对应的 Key时，取参数 3
         * 参数 4、对应的国际化，为 null时，去默认的资源文件
         */
        String message = StaticBean.resourceBundleMessageSource.getMessage(errorCode, args, "default", locale);
        SelfException selfException = new SelfException(errorCode, message);
        throw selfException;
    }

}
