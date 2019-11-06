package com.noah_solutions.websockt;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/cart")
public class CheckCenterController {

    //页面请求
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav=new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }
    //推送数据接口
    @RequestMapping("/send")
    public JSONObject pushToWeb(@PathVariable String cid, String message) {
        try {
            WebSocketServer.sendInfo(message,cid);
        } catch (IOException e) {
            throw  new CustormException("websocket IO异常",1);
        }
        return CodeAndMsg.SUCESS.getJSON();
    }
}