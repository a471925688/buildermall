package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.IEmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("email")
public class EmailController {

    @Resource
    private IEmailService emailService;

    //發貨提醒
    @PostMapping("/shippingReminder.do")
    public JSONObject shippingReminder(@RequestParam("orderId") String  orderId, HttpServletRequest request){
        try {
            emailService.shippingReminder(getCookie(request,"lang"),orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CodeAndMsg.SUCESS.getJSON();
    }
















    private String getCookie(HttpServletRequest request,String key){
        String value = "";
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(key)){
                    value = cookie.getValue();
                }
            }
        }

        return value;
    }
}
