package com.ztesoft.spring.upload.common;

import java.util.Locale;

/**
 * @author tian.lue
 */
public abstract class LocaleCache {

    public static Locale locale;

    public static Locale DEFAULT_LOCALE;

    static {
        DEFAULT_LOCALE = new Locale("en");
    }

    public static Locale getLocale() {
        if (null == locale) {
            return DEFAULT_LOCALE;
        }
        return locale;
    }

}
