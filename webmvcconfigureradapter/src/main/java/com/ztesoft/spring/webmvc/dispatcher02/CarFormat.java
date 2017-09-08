package com.ztesoft.spring.webmvc.dispatcher02;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tian.lue
 */
public class CarFormat implements Formatter<Car> {

    private String regexp;

    public CarFormat(String regexp) {
        this.regexp = regexp;
    }

    /**
     * 将 Car转换为 String对象
     * @param car
     * @param locale
     * @return
     */
    public String print(Car car, Locale locale) {
        if (null == car) {
            return "";
        }
        return new StringBuffer().append(car.getBrand()).append("--").append(car.getPrice()).toString();
    }

    /**
     * 将 String对象转为 Car对象
     * @param source
     * @param locale
     * @return
     */
    public Car parse(String source, Locale locale) {
        if(!StringUtils.hasLength(source)) {
            //①如果source为空 返回null
            return null;
        }
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(source);
        if(matcher.matches()) {
            //②如果匹配 进行转换
            Car car = new Car();
            car.setBrand(matcher.group(1));
            car.setPrice(matcher.group(2));
            return car;
        } else {
            //③如果不匹配 转换失败
            throw new IllegalArgumentException(String.format("类型转换失败，需要格式[w+--d+]，但格式是[%s]", source));
        }
    }

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

}
