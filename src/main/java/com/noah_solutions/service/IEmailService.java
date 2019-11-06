package com.noah_solutions.service;

import com.noah_solutions.bean.DataCache;
import com.noah_solutions.entity.DealingSlip;
import com.noah_solutions.entity.Order;
import com.noah_solutions.entity.System;
import com.noah_solutions.entity.User;

public interface IEmailService {
    //發貨提醒
    void shippingReminder(String lang,String orderId)throws Exception;
    //新訂單提醒
    void newOrderReminder(String lang,String orderId)throws Exception;
    void newOrder(String lang, Order order)throws Exception;
    void shipmentOrder(String orderId)throws Exception;
    //非會員購買
    void nonMemberPurchase(String lang, DataCache data,Boolean newUser)throws Exception;

    void modifyPasswordByEmail(String lang, User user)throws Exception;
    //發送收據
    void sendReceipt( DealingSlip dealingSlip)throws Exception;
    //超額提醒
    void excessReminder(DealingSlip dealingSlip, System system)throws Exception;
    //评价提醒
    void evaluateEmail(String lang,String orderId,User user)throws Exception;
    //发送聊天消息提醒
    void sendCartMesseng(String email)throws Exception;
}
