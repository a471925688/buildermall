package com.noah_solutions.webcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.User;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.service.IAdvertisingService;
import com.noah_solutions.service.IEmailService;
import com.noah_solutions.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.noah_solutions.ex.CodeAndMsg.SUCESS;

@RestController
@RequestMapping("/webOrder")
public class WebOrderController {

    @Resource
    private IEmailService emailService;
    @Resource
    private IOrderService orderService;

    @ApiOperation(value="产品评价提醒", notes="产品评价提醒")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/sendEvaluateEmail.do")
    public JSONObject  sendEvaluateEmail(@RequestParam("orderId")String orderId,String lang, HttpSession session, HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        emailService.evaluateEmail(lang,orderId,getAndCheckLoginUser(session));
        return SUCESS.getJSON();
    }

    @ApiOperation(value="通過訂單id獲取訂單信息", notes="通過訂單id獲取訂單信息")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getOrderById.do")
    public JSONObject  getOrderById(@RequestParam("orderId")String orderId, HttpSession session, HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        getAndCheckLoginUser(session);
        return SUCESS.getJSON(orderService.getOrderById(orderId));
    }

    private User getAndCheckLoginUser(HttpSession session)throws Exception{
        if(session.getAttribute(ProjectConfig.LOGIN_USER)==null)
            throw new CustormException(CodeAndMsg.ERROR_CHECK_USER);
        return (User) session.getAttribute(ProjectConfig.LOGIN_USER);
    }
}
