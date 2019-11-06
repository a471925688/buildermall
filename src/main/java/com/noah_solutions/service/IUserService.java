package com.noah_solutions.service;

import com.noah_solutions.bean.DataCache;
import com.noah_solutions.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IUserService {

    //查询所有用户
    List<User> selectAllUser();

    Page<User> selectUserPageByCont(User condition, Pageable pageable);
    //通过id查询单个用户
    User selectUserByUserId(String userId);
    //根据条件获取总数
    Long selectCountUserByCont(User condition);
    //.查詢登錄次數
    Long getCountLogin();
    //更新用户
    void addUser(User user, Login login,List<ReceivingInfo> receivingInfos)throws Exception;
    //更新用户
    void updateUser(User user, Login login,List<ReceivingInfo> receivingInfos)throws Exception;
    //改变用户状态
    void changeUserIsProhibit(Boolean userIsProhibit, String userId)throws Exception;
    //删除单个用户
    void delUserByUserId(String userId);
    //批量删除用户
    void delAllUserByUserId(List<String> userIds);

//    Long countAllByUserTypeNotMember();





    //註冊用戶
    User registerUser(User user)throws Exception;
    //用户登录
    User userLogin(String loginUserName, String loginPassWord)throws Exception;
    //保存收貨地址信息
    ReceivingInfo addReceivingInfo(ReceivingInfo receivingInfo);
    //刪除收貨地址信息
    void delReceivingInfoByRecId(String recId);
    //提交訂單
    DealingSlip webAddOrder(String lang,String cur,Order order,User user, HttpSession session)throws Exception;
    //訂單二次付款
    DealingSlip paymentOrder(String lang,String cur,List<String>   orderIds,String userId)throws Exception;

    Page<Order> selectOrder(Order order,String title,Pageable pageable);
    //取消訂單
    void cancelOrder(User user,String orderId);
    //確認收貨
    void receivingGoodsOrder(User andCheckLoginUser, String orderId);
    //退款申請
    void refundOrder(User user, String orderId,String orderRecExplanation);
    //非會員購買
    User nonMemberPurchase(String lang,String email, DataCache data)throws Exception;
    //修改密碼（發送郵件）
    void modifyPasswordByEmail(String lang, String email)throws Exception;
    //修改密碼
    User modifyPassword(User user,String newPassWord)throws Exception;

    User editUserInfo(User oldUser)throws Exception;



}
