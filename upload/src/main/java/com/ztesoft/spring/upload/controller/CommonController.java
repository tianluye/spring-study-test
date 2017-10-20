package com.ztesoft.spring.upload.controller;

import com.alibaba.fastjson.JSONObject;
import com.ztesoft.spring.upload.common.LocaleCache;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author tian.lue
 */
@RestController
public class CommonController {

    @RequestMapping(
        value = {"locale"},
        method = { RequestMethod.GET }
    )
    public Map<String, Object> getLocalInfo(HttpServletRequest request) {
        HashMap<String, Object> json = new HashMap<String, Object>();
        String lang = LocaleCache.getLocale().getLanguage();
        json.put("language", lang);
        return json;
    }

    @RequestMapping(
        value = {"locale"},
        method = { RequestMethod.POST }
    )
    public Locale changeLocalInfo(@RequestBody JSONObject param) {
        String lan = param.getString("language");
        if (!StringUtils.isEmpty(lan)) {
            Locale locale = new Locale(lan);
            LocaleCache.locale = locale;
        }
        return LocaleCache.locale;
    }

}
