package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.chat.FriendMessage;
import com.noah_solutions.entity.chat.FriendType;
import com.noah_solutions.entity.chat.Group;
import com.noah_solutions.entity.chat.SysMessage;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.pojo.CartUser;
import com.noah_solutions.pojo.Message;
import com.noah_solutions.service.ICartService;
import com.noah_solutions.websockt.WebSocketServer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static com.noah_solutions.ex.CodeAndMsg.HANDLESUCESS;
import static com.noah_solutions.ex.CodeAndMsg.SUCESS;

/**
 * 2018 12 20 lijun
 * 品牌相關控制器
 */
@RestController
@RequestMapping("/cart")
public class CartController extends BaseController{

    @Resource
    private ICartService cartService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////

    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @RequestMapping("/getInitCartData.do")
    public JSONObject getInitCartData(HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        return SUCESS.getJSON(cartService.getInitCartData( cartService.getCartUserByAdminId(admin.getAdminId())));
    }
    @RequestMapping("/findUser.do")
    public JSONObject findUser(
                                @RequestParam(value = "id",required = false) String val,
                                @RequestParam(value = "page",defaultValue = "1") Integer page,
                                @RequestParam(value = "limit",defaultValue = "12") Integer limit,HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        return SUCESS.getJSON(cartService.findCartUser(val, page,limit));
    }
    //獲取消息
    @RequestMapping("/getSysMessage.do")
    public JSONObject getSysMessage(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "12") Integer limit,HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        return SUCESS.getJSON(cartService.getSysMessage(Integer.valueOf(admin.getCartUser().getId()),PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC, "id"))));
    }
    //獲取未讀消息數量
    @RequestMapping("/getCountNotReadSysMessage.do")
    public JSONObject getCountNotReadSysMessage(HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        return SUCESS.getJSON(cartService.getCountNotReadSysMessage(Integer.valueOf(admin.getCartUser().getId())));
    }
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    /**
     * 獲取未讀消息
     * @return
     * @throws Exception
     */
    @RequestMapping("/getOfflineMessage.do")
    public JSONObject getOfflineMessage(HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        WebSocketServer.sendInfo(CodeAndMsg.SUCESS.getCartJSON(cartService.getOfflineMessage(admin.getCartUser().getId()),ProjectConfig.SendMessageType.OFFLINEMESSAGE.getValue()).toJSONString(),admin.getCartUser().getId());
        return SUCESS.getJSON();
    }

    /**
     * 發送好友消息
     * @param message
     * @return
     * @throws Exception
     */
    @RequestMapping("/send.do")
    public JSONObject send(Message message,HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        FriendMessage friendMessage = new FriendMessage(Integer.valueOf(admin.getCartUser().getId()),Integer.valueOf(message.getTo()),message.getContent(),message.getType(),message.getOrderId());
        cartService.addFriendMessage(friendMessage);
        WebSocketServer.sendInfo(CodeAndMsg.SUCESS.getCartJSON(message,ProjectConfig.SendMessageType.CART.getValue()).toJSONString(),message.getTo());
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
    public JSONObject markReaded(Integer toId,HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        cartService.markReaded(Integer.valueOf(admin.getCartUser().getId()),toId);
         return SUCESS.getJSON();
    }


    /**
     * 添加好友申请
     * @param message
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/add.do")
    public JSONObject addFriendInfo(SysMessage message,HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        message.setAvatar(admin.getAdminHeadPortraitUrl());
        message.setFrom(Integer.valueOf(admin.getCartUser().getId()));
        message.setUsername(admin.getAdminRealName());
        message.setContent(ProjectConfig.CartMessageType.ADDFRIEND.queryValue());
        cartService.addFriendInfo(message);
        WebSocketServer.sendInfo(CodeAndMsg.SUCESS.getCartJSON(ProjectConfig.SendMessageType.UPDATEMSG.getValue()).toJSONString(),message.getUid()+"");
        return SUCESS.getJSON();
    }

    /**
     * 拒绝好友申请
     * @param message
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/refuseFriend.do")
    public JSONObject refuseFriend(SysMessage message,String oldId,HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        cartService.refuseFriend(oldId);
        message.setContent(ProjectConfig.CartMessageType.AUDREFUSE.queryValue());
        message.setUsername(admin.getAdminRealName());
        cartService.addSysMessage(message);
        WebSocketServer.sendInfo(CodeAndMsg.SUCESS.getCartJSON(ProjectConfig.SendMessageType.UPDATEMSG.getValue()).toJSONString(),message.getUid()+"");
        return SUCESS.getJSON();
    }

    /**
     * 同意添加好友
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/agreeFriend.do")
    public JSONObject agreeFriend(@RequestParam("id")String id,@RequestParam("typeId")String typeId,HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        SysMessage message =  cartService.agreeFriend(id,typeId);
        //推送到前端
        CartUser cartUser = admin.getCartUser();
        cartUser.setTypeId(Integer.valueOf(message.getGroupId()));
        WebSocketServer.sendInfo(CodeAndMsg.SUCESS.getCartJSON(cartUser,ProjectConfig.SendMessageType.ADDFRIEND.getValue()).toJSONString(),message.getFrom()+"" );
        //添加系統消息
        cartService.addSysMessage(new SysMessage(ProjectConfig.CartMessageType.AGREEFRIEND.queryValue(),admin.getAdminRealName(),message.getFrom()));
        return SUCESS.getJSON();
    }


    /**
     * 添加分组
     * @param friendType
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/addFriendType.do")
    public JSONObject addFriendType(FriendType friendType, HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        friendType.setUserId(Integer.valueOf(admin.getCartUser().getId()));
        return SUCESS.getJSON(cartService.addFriendType(friendType));
    }

    /**
     * 删除分组
     * @param id
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/delFriendType.do")
    public JSONObject delFriendType(@RequestParam("id") Integer id, HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        cartService.delFriendType(id,admin.getCartUser().getId()+"");
        return SUCESS.getJSON();
    }

    /**
     * 更新好友分组
     * @param friendId
     * @param typeId
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/updateFriendType.do")
    public JSONObject updateFriendType(@RequestParam("friendId") Integer friendId,@RequestParam("typeId") Integer typeId, HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        cartService.updateFriendType(admin.getCartUser().getId()+"",friendId,typeId);
        return HANDLESUCESS.getJSON();
    }

    /**
     *  分组改名
     * @param id
     * @param typeName
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/renameFriendType.do")
    public JSONObject renameFriendType(@RequestParam("id") Integer id,@RequestParam("typeName") String typeName, HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        cartService.renameFriendType(admin.getCartUser().getId()+"",id,typeName);
        return HANDLESUCESS.getJSON();
    }

    /**
     * 刪除好友
     * @param friendId
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("/delFriend.do")
    public JSONObject delFriend(@RequestParam("friendId") Integer friendId, HttpSession session)throws Exception{
        Admin admin = getAndCheckLoginAdmin(session);
        cartService.delFriend(admin.getCartUser().getId()+"",friendId);
        WebSocketServer.sendInfo(CodeAndMsg.SUCESS.getCartJSON(admin.getCartUser().getId(),ProjectConfig.SendMessageType.DELFRIEND.getValue()).toJSONString(),friendId+"" );
        return HANDLESUCESS.getJSON();
    }

    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}
