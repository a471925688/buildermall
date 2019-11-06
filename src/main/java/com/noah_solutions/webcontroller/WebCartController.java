package com.noah_solutions.webcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.controller.BaseController;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.User;
import com.noah_solutions.entity.chat.FriendMessage;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.pojo.Message;
import com.noah_solutions.service.IAdvertisingService;
import com.noah_solutions.service.ICartService;
import com.noah_solutions.websockt.WebSocketServer;
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
@RequestMapping("/webCart")
public class WebCartController extends BaseController {

    @Resource
    private ICartService cartService;

    @ApiOperation(value="根據adminId獲取cartUser信息", notes="根據adminId獲取cartUser信息")
    @ApiResponses({
            @ApiResponse(code = 1, message = "ok"),
            @ApiResponse(code = 0, message = "平台異常"),
    })
    @PostMapping("/getCartUserByAdminId.do")
    public JSONObject getCartUserByAdminId(@RequestParam("adminId") String adminId, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Credentials","true");
        return CodeAndMsg.SUCESS.getJSON(cartService.getCartUserByAdminId(adminId));
    }


    /**
     * 發送好友消息
     * @param message
     * @return
     * @throws Exception
     */
    @RequestMapping("/send.do")
    public JSONObject send(Message message, HttpSession session, HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user = getAndCheckLoginUser(session);
        FriendMessage friendMessage = new FriendMessage(Integer.valueOf(user.getCartUser().getId()),Integer.valueOf(message.getTo()),message.getContent(),message.getType(),message.getOrderId());
        cartService.addFriendMessage(friendMessage);
        WebSocketServer.sendInfo(CodeAndMsg.SUCESS.getCartJSON(message, ProjectConfig.SendMessageType.CART.getValue()).toJSONString(),message.getTo());
        return SUCESS.getJSON();
    }

    /**
     * 獲取未讀消息
     * @return
     * @throws Exception
     */
    @RequestMapping("/getOfflineMessage.do")
    public JSONObject getOfflineMessage(HttpSession session, HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user = getAndCheckLoginUser(session);
        WebSocketServer.sendInfo(CodeAndMsg.SUCESS.getCartJSON(cartService.getOfflineMessage(user.getCartUser().getId()),ProjectConfig.SendMessageType.OFFLINEMESSAGE.getValue()).toJSONString(),user.getCartUser().getId());
        return SUCESS.getJSON();
    }

    /**
     * 标记好友消息已读
     * @param toId
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/markReaded.do")
    public JSONObject markReaded(Integer toId,HttpSession session, HttpServletResponse response)throws Exception{
        response.setHeader("Access-Control-Allow-Credentials","true");
        User user = getAndCheckLoginUser(session);
        cartService.markReaded(Integer.valueOf(user.getCartUser().getId()),toId);
        return SUCESS.getJSON();
    }

}
