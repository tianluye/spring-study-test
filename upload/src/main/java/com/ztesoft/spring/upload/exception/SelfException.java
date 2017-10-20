package com.ztesoft.spring.upload.exception;

import java.io.Serializable;

/**
 * @author tian.lue
 */
public class SelfException extends Exception implements Serializable {

    private String errorCode;

    private String localeMessage;

    public SelfException(String errorCode, String localeMessage) {
        super(localeMessage);
        this.errorCode = errorCode;
        this.localeMessage = localeMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getLocaleMessage() {
        return localeMessage;
    }

    public void setLocaleMessage(String localeMessage) {
        this.localeMessage = localeMessage;
    }
}
