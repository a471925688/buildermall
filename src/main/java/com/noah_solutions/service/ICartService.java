package com.noah_solutions.service;

import com.noah_solutions.common.TablePage;
import com.noah_solutions.entity.chat.FriendMessage;
import com.noah_solutions.entity.chat.FriendType;
import com.noah_solutions.entity.chat.SysMessage;
import com.noah_solutions.pojo.CartInitData;
import com.noah_solutions.pojo.CartUser;
import com.noah_solutions.pojo.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ICartService {
    CartInitData getInitCartData(CartUser user);

    TablePage<CartUser> findCartUser(String id, Integer page, Integer limit);

    List<SysMessage> getSysMessage(Integer id,Pageable pageable);

    Long getCountNotReadSysMessage(Integer id);

    void addFriendInfo(SysMessage message);//添加好友申請

    void addSysMessage(SysMessage message);

    FriendType addFriendType(FriendType friendType);

    void delFriendType(Integer id, String adminId);

    void updateFriendType(String userId, Integer friendId, Integer typeId);

    void renameFriendType(String userId, Integer id, String typeName);

    void delFriend(String userId, Integer friendId);

    SysMessage agreeFriend(String id,String typeId);

    void refuseFriend(String oldId);

    void addFriendMessage(FriendMessage friendMessage)throws Exception;

    CartUser getCartUserByAdminId(String adminId);

    void initCartUser();

    List<Message> getOfflineMessage(String id);

    void markReaded(Integer id, Integer toId);

    void updateStatus(String cartUserId,Integer status);

    void setOfflineAll();
    void  addCart(com.noah_solutions.entity.chat.CartUser cartUser);

    CartUser getCartUserByUserId(String userId);
}
