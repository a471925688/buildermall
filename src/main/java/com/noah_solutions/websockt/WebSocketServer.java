package com.noah_solutions.websockt;

import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint(value = "/websocket/{sid}")
@Component
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;


    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid="";


    //你要注入的service或者dao
    private static ICartService cartService;

    // 注入的时候，给类的 service 注入
    @Autowired
    public void setChatService(ICartService chatService) {
        WebSocketServer.cartService = chatService;
    }



    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("鏈接websocket成功，當前人數"+onlineCount);
        cartService.updateStatus(sid,1);
        this.sid=sid;
        try {
            sendInfo(CodeAndMsg.SUCESS.getCartJSON(sid, ProjectConfig.SendMessageType.ONLINE.getValue()).toJSONString(),null);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            sendMessage("连接成功");
//        } catch (IOException e) {
//            throw  new CustormException("websocket IO异常",1);
//        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        cartService.updateStatus(sid,0);
        subOnlineCount();           //在线数减1
        try {
            sendInfo(CodeAndMsg.SUCESS.getCartJSON(sid, ProjectConfig.SendMessageType.OFFLINE.getValue()).toJSONString(),null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        if(!StringUtils.isEmpty(sid)){
            webSocketSet.remove(this);  //从set中删除
            cartService.updateStatus(sid,0);
        }
        throw  new CustormException("websocket 未知错误",1);
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static boolean sendInfo(String message,@PathParam("sid") String sid) throws IOException {
        Boolean res = false;
        for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(sid==null) {
                    item.sendMessage(message);
                }else if(item.sid.equals(sid)){
                    item.sendMessage(message);
                    res = true;
                }
            } catch (IOException e) {
                throw  new CustormException("websocket IO异常",1);
            }
        }
        if(sid==null){
            return true;
        }else {
            return  res;
        }
    }
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
