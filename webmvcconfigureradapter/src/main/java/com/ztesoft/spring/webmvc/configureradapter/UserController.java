package com.ztesoft.spring.webmvc.configureradapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tian.lue
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private Car car;
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User view(@PathVariable("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setName("zhang");
        user.setCar(car);
        return user;
    }
}
