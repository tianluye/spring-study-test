package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tian.lue
 */
@RestController
@RequestMapping("/ctr")
public class MainController {

    @Autowired
    @Qualifier("AoDi")
    private Car aoDi;

    @Autowired
    @Qualifier("DaZhong")
    private Car daZhong;

    /**
     * 因为在配置类中开启了 @EnableWebMvc注解，在它的导入配置类里实例化了 ConversionService这个 bean实例
     */
    @Autowired
    private ConversionService conversionService;

    @RequestMapping("/user")
    public User showUser() {
        User user = new User();
        user.setUserName("Tom");
        user.setAge(18L);
        final List<Car> carList = new ArrayList<Car>(1);
        Car car  = conversionService.convert("asd--456123", Car.class);
        carList.add(aoDi);
        carList.add(daZhong);
        carList.add(car);
        String result = conversionService.convert(car, String.class);
        System.out.println(result); // asd--456123
        user.setCarList(carList);
        user.setBirthDay(new Date());
        return user;
    }

    @RequestMapping("/path/match/{userName}")
    public User pathMatch(@PathVariable String userName) {
        User user = new User();
        user.setUserName(userName); // tom.asd --> tom; tom..asd --> tom.
        return user;
    }

    @RequestMapping(value = "/convert", produces = { "application/x-self" })
    public @ResponseBody Person convert(@RequestBody Person person) {
        return person;
    }

}
