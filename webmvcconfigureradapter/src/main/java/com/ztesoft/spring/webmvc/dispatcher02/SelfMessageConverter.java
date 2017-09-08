package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author tian.lue
 */
public class SelfMessageConverter extends AbstractHttpMessageConverter<Person> {

    //自定义媒体类型
    public SelfMessageConverter(){
        super(new MediaType("application", "x-self", Charset.forName("UTF-8")));
    }

    //只支持 Person类
    @Override
    protected boolean supports(Class<?> clazz) {
        return Person.class.isAssignableFrom(clazz);
    }

    // 读取传入的参数，并自定义规则，将其转换为 Person对象
    @Override
    protected Person readInternal(Class<? extends Person> clazz, HttpInputMessage message) throws IOException,
        HttpMessageNotReadableException {
        String temp = StreamUtils.copyToString(message.getBody(), Charset.forName("UTF-8"));
        String[] tempArr = temp.split("-");
        Person person = new Person(tempArr[0], tempArr[1]);
        return person;
    }

    // 自定义规则，处理 Person对象的输出信息
    @Override
    protected void writeInternal(Person person, HttpOutputMessage message) throws IOException,
        HttpMessageNotWritableException {
        String result = new StringBuffer().append("Hello: ").append(person.getFirstName())
            .append("-").append(person.getLastName()).toString();
        message.getBody().write(result.getBytes());
    }

}
