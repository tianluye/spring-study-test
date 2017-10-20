package com.ztesoft.spring.upload.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztesoft.spring.upload.exception.ValidateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author tian.lue
 */
@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @RequestMapping(value = "/imgsrc", method = RequestMethod.GET)
    public JSONArray getImgSrc() {
        JSONArray imgSrc = new JSONArray();
        imgSrc.add("../../../../images/t01.png");
        imgSrc.add("../../../../images/t02.png");
        imgSrc.add("../../../../images/t03.png");
        imgSrc.add("../../../../images/t04.png");
        imgSrc.add("../../../../images/t05.png");
        return imgSrc;
    }

    @RequestMapping(value = "/error/msg", method = RequestMethod.GET)
    public JSONObject sendErrorMsg() throws Exception {
        JSONObject result = new JSONObject();
        double ran = Math.random() * 10;
        if (ran < 0) {
            result.put("result", "success");
        } else {
            ValidateUtil.isTrue(false, "SPRING-LOCALE-10001");
        }
        return result;
    }

}
