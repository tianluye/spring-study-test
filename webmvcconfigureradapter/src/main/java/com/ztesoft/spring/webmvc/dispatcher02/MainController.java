package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author tian.lue
 * 参考注解代码，@RestController注解，会默认为所有的方法返回值加@ResponseBody注解
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

    @Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;

    @RequestMapping(value = "/convert", produces = { "application/x-self" }, method = RequestMethod.POST)
    public @ResponseBody Person convert(@RequestBody @Valid Person person, BindingResult br) {
        // BindingResult存放校验 Person规则的错误信息
        if (br.hasErrors()) { // 是否存在错误信息
            List<ObjectError> errorList = br.getAllErrors();
            for (ObjectError error : errorList) {
                String msgCode = error.getCode(); // 获取错误信息 code
                // 获取其对应在国际化资源文件中的 message
                /**
                 * 参数 1、对应资源文件中的 Key
                 * 参数 2、对应医院文件中的 Value里的占位符 {}
                 * 参数 3、当资源文件中找不到参数 1对应的 Key时，取参数 3
                 * 参数 4、对应的国际化，为 null时，去默认的资源文件
                 */
                String message = resourceBundleMessageSource.getMessage(msgCode, new Object[] {"**Test**"},
                    "default", Locale.CHINESE);
                System.out.println(message);
            }
        }
        return person;
    }

    /**
     * http://localhost:8080/dispatcher02/ctr/contentNegotiation.html
     * http://localhost:8080/dispatcher02/ctr/contentNegotiation
     * 结果为： Test ContentNegotiation!
     * http://localhost:8080/dispatcher02/ctr/contentNegotiation.json
     * 结果为： {"1":"a","2":"b"}
     * @return
     */
    @GetMapping("/contentNegotiation")
    public ModelAndView contentNegotiationTest() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "a");
        map.put("2", "b");
        return new ModelAndView("contentNegotiation", map);
    }

    /**
     * 当参数日期为 "yyyy---MM---dd HH:mm:ss"格式的时候，可以将其转为日期
     * @param binder
     * @throws Exception
     */
    @InitBinder(value = {"teacher", "student"})
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy---MM---dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    @PostMapping("/method/argument")
    public School methodArgumentTest(@MyForm Teacher teacher, @MyForm Student student) {
        return new School(teacher, student);
    }

    @GetMapping("/return/value")
    @MyResponseBody
    @StudentPrefix("Hello")
    public Student testReturnValue() {
        List<String> love = new ArrayList<String>();
        love.add("QQ");
        love.add("WeiXin");
        Student student = new Student("Tom", 20, new Date(), love);
        return student;
    }

}
