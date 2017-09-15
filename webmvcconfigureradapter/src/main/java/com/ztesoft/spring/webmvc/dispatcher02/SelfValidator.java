package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author tian.lue
 */
@Component
public class SelfValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        /**
         * Class1.isAssignableFrom(Class2) --> 判断一个类 Class1和另一个类 Class2是否相同
         * 或Class2是Class1的子类或接口
         *
         * instanceof是用来判断一个对象实例是否是一个类或接口的或其子类子接口的实例
         * o instanceof Class 实例 o 是 Class 或其子类子接口的实例
         */
        return Person.class.isAssignableFrom(clazz);
    }

    public void validate(Object object, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "lastName.empty");
        Person person = (Person) object;
        if (person.getFirstName().length() > 4) {
            errors.rejectValue("firstName", "qqq");
        } else {
            errors.rejectValue("firstName", "bbb");
        }
    }

}
