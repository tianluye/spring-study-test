package com.ztesoft.spring.upload.exception;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author tian.lue
 */
@RestControllerAdvice
public class SelfResponseEntityHandler extends ResponseEntityExceptionHandler implements ResponseBodyAdvice {

    public static final HttpStatus DEFAULT_HTTP_STATUS;

    static {
        DEFAULT_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @ExceptionHandler({SelfException.class})
    public ResponseEntity<SelfResponseEntityHandler.ErrorMessage> handleMyException(SelfException ex) {
        SelfResponseEntityHandler.ErrorMessage errorMessage =
            new SelfResponseEntityHandler.ErrorMessage(ex.getErrorCode(), ex.getLocaleMessage());
        return new ResponseEntity(errorMessage, DEFAULT_HTTP_STATUS);
    }

    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType,
        ServerHttpRequest request, ServerHttpResponse response) {
        if(body == null && request.getMethod().equals(HttpMethod.GET)) {
            response.setStatusCode(HttpStatus.NO_CONTENT);
        }
        return body;
    }

    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    protected class ErrorMessage {
        String code;
        String message;

        public ErrorMessage(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

}
