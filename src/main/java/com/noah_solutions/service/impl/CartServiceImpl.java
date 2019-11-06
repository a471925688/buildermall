package com.noah_solutions.service.impl;

import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.common.TablePage;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.chat.FriendMessage;
import com.noah_solutions.entity.chat.FriendType;
import com.noah_solutions.entity.chat.SysMessage;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.pojo.*;
import com.noah_solutions.repository.AdminRepository;
import com.noah_solutions.repository.UserRepository;
import com.noah_solutions.repository.cart.*;
import com.noah_solutions.service.ICartService;
import com.noah_solutions.service.IEmailService;
import com.noah_solutions.util.BeanUtils;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements ICartService {

    @Resource
    private FriendTypeRepository friendTypeRepository;
    @Resource
    private GroupRepository groupRepository;

    @Resource
    private UserRepository userRepository;
    @Resource
    private AdminRepository adminRepository;
    @Resource
    private SysMessageRepository messageRepository;
    @Resource
    private FriendRepository friendRepository;

    @Resource
    private FriendMessageRepository friendMessageRepository;
    @Resource
    private CartUserRepository cartUserRepository;

    @Resource
    private IEmailService emailService;




    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    @Override
    public CartInitData getInitCartData(CartUser cartUser) {
        List<FriendType> friendType = friendTypeRepository.getAllByUserId(Integer.valueOf(cartUser.getId()));
        List<Group> group = groupRepository.selectByUserId(Integer.valueOf(cartUser.getId()));
        List<Friend> friend = new ArrayList<>();
        friendType.forEach(v->{
            List<CartUser> users = new ArrayList<>();
            v.getFriends().forEach(f->{
                com.noah_solutions.entity.chat.CartUser cartFriend  = f.getCartFriend();

                if(!StringUtils.isEmpty(cartFriend.getAdminId())){
                    users.add(new CartUser(cartFriend.getCartUserId()+"",cartFriend.getAdmin().getAdminRealName(),cartFriend.getSign(),StringUtils.isEmpty(cartFriend.getAdmin().getAdminHeadPortraitUrl())?"http://tva3.sinaimg.cn/crop.0.0.180.180.180/7f5f6861jw1e8qgp5bmzyj2050050aa8.jpg":cartFriend.getAdmin().getAdminHeadPortraitUrl(),cartFriend.getStatus()));
                }else {
                    users.add(new CartUser(cartFriend.getCartUserId()+"",cartFriend.getUser().getUserRealName(),cartFriend.getSign(),StringUtils.isEmpty(cartFriend.getUser().getUserHeadPortraitUrl())?"http://tva3.sinaimg.cn/crop.0.0.180.180.180/7f5f6861jw1e8qgp5bmzyj2050050aa8.jpg":cartFriend.getUser().getUserHeadPortraitUrl(),cartFriend.getStatus()));
                }
           });
            friend.add(new Friend(v.getId(),v.getTypeName(),users));
        });
        CartInitData cartInitData = new CartInitData(cartUser,friend,group);

        return cartInitData;
    }

    @Override
    public TablePage<CartUser> findCartUser(String val, Integer page, Integer limit) {
        if(val==null){
            val="";
        }
        List<CartUser> cartUsers = new ArrayList<>();
        Long totle = 0L;
        int size = limit;
        Page<CartUser> carts = cartUserRepository.searchAllAdmin(val,PageRequest.of(page-1,limit));
        cartUsers.addAll(carts.getContent());
        totle+=carts.getTotalElements();
        limit = limit - carts.getContent().size();
        if(limit>0){
            carts = cartUserRepository.searchAllUser(val,PageRequest.of(page-1,limit));
            cartUsers.addAll(carts.getContent());
            totle+=carts.getTotalElements();
        }else {
            totle+=cartUserRepository.countSearchAllUser(val);
        }
        return    new TablePage<>(totle,page,size,cartUsers);
    }

    @Override
    public List<SysMessage> getSysMessage(Integer id,Pageable pageable) {
        List<SysMessage> messages = messageRepository.findAllByUid(id,pageable);
        messages.forEach(v->{
            v.setRead(true);
            if(!StringUtils.isEmpty(v.getFrom())){
                v.setUser(cartUserRepository.selectUserById(v.getFrom()+""));
                if(v.getUser()==null){
                    v.setUser(cartUserRepository.selectAdminById(v.getFrom()+""));
                }

//                com.noah_solutions.entity.chat.CartUser user = cartUserRepository.getOne(v.getFrom());
//                if(StringUtils.isEmpty(user.getUserId())){
//                    v.setUser(new CartUser(user.getCartUserId()+"",user.getAdmin().getAdminRealName(),user.getSign(),user.getAdmin().getAdminHeadPortraitUrl(),user.getStatus()));
//                }else {
//                    v.setUser(new CartUser(user.getCartUserId()+"",user.getUser().getUserRealName(),user.getSign(),user.getUser().getUserHeadPortraitUrl(),user.getStatus()));
//                }
            }
        });
        messageRepository.flush();
        return  messages;
    }

    @Override
    public Long getCountNotReadSysMessage(Integer id) {
        return messageRepository.countByUidAndRead(id,false);
    }



    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////




    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////

    @Override
    @Transactional
    public void addFriendInfo(SysMessage message) {
        if(messageRepository.existsByUidAndFromAndStatus(message.getUid(),message.getFrom(),ProjectConfig.CartMessageStatus.UNTREATED.getValue())){
            throw new CustormException("您已經申請添加對方為好友,請耐心等待!",1);
        }
        if(friendRepository.existsByUserIdAndFriendId(message.getUid()+"",message.getFrom()+"")){
            throw new CustormException("對方已經是您的好友!",1);
        }
        addSysMessage(message);
    }

    @Override
    @Transactional
    public void addSysMessage(SysMessage message) {
        messageRepository.save(message);
    }

    @Override
    @Transactional
    public FriendType addFriendType(FriendType friendType) {
        friendType.setIsDefault(0);
        friendType = friendTypeRepository.saveAndFlush(friendType);
        return friendType;
    }


    @Override
    @Transactional
    public void delFriendType(Integer id, String adminId) {
        friendTypeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateFriendType(String adminId, Integer friendId, Integer typeId) {
        com.noah_solutions.entity.chat.Friend friend = friendRepository.findByFriendIdAndUserId(friendId+"", adminId);
        friend.setTypeId(typeId);
        friendRepository.flush();
    }

    @Override
    @Transactional
    public void renameFriendType(String userId, Integer id, String typeName) {
        friendTypeRepository.renameFriendType(typeName,id,Integer.valueOf(userId));
    }

    @Override
    @Transactional
    public void delFriend(String userId, Integer friendId) {
        friendRepository.deleteByUserIdAndFriendId(userId,friendId+"");
        friendRepository.deleteByUserIdAndFriendId(friendId+"",userId);
    }

    @Override
    public SysMessage agreeFriend(String id,String typeId){
        SysMessage message =  messageRepository.getOne(Integer.valueOf(id));
        if(!friendRepository.existsByUserIdAndFriendId(message.getFrom()+"",message.getUid()+""))
            friendRepository.save(new com.noah_solutions.entity.chat.Friend(message.getFrom()+"",message.getUid()+"",Integer.valueOf(message.getGroupId())));
        if(!friendRepository.existsByUserIdAndFriendId(message.getUid()+"",message.getFrom()+""))
            friendRepository.save(new com.noah_solutions.entity.chat.Friend(message.getUid()+"",message.getFrom()+"",Integer.valueOf(typeId)));
        message.setStatus(ProjectConfig.CartMessageStatus.PASS.getValue());
        return  message;
    }

    @Override
    @Transactional
    public void refuseFriend(String oldId) {
        messageRepository.updateStatus(ProjectConfig.CartMessageStatus.REFUSE.getValue(), Integer.valueOf(oldId));
    }

    @Override
    public void addFriendMessage(FriendMessage friendMessage)throws Exception {
        //保存聊天消息
        friendMessageRepository.save(friendMessage);
        //判断用户是否在线
        if(cartUserRepository.existsByCartUserIdAndStatus(friendMessage.getToUserId()+"",ProjectConfig.CartUserStatus.OFFLINE.getValue()+"")){
            com.noah_solutions.entity.chat.CartUser user = cartUserRepository.findById(friendMessage.getToUserId()+"").get();
            String email = "";
            if(StringUtils.isEmpty(user.getUserId())){
                email = adminRepository.findEmailById(user.getAdminId());
            }else {
                email = userRepository.findEmailById(user.getAdminId());
            }
            //发送邮件提醒
            emailService.sendCartMesseng(email);
        }

    }

    @Override
    public CartUser getCartUserByAdminId(String adminId) {
        return cartUserRepository.selectByAdminId(adminId);
    }

    @Override
    @Transactional
    public void initCartUser() {
        adminRepository.findAll().forEach(v->{
            if(!cartUserRepository.existsByAdminId(v.getAdminId())){
                addCart(new com.noah_solutions.entity.chat.CartUser("暫無簽名",null,v.getAdminId()));
//                com.noah_solutions.entity.chat.CartUser cartUser = cartUserRepository.save(new com.noah_solutions.entity.chat.CartUser("暫無簽名",null,v.getAdminId()));
//                friendTypeRepository.save(new FriendType("我的好友",Integer.valueOf(cartUser.getCartUserId())));
            }
       });
        userRepository.findAll().forEach(v->{
            if(!cartUserRepository.existsByUserId(v.getUserId())){
                addCart(new com.noah_solutions.entity.chat.CartUser("暫無簽名",v.getUserId(),null));
//                com.noah_solutions.entity.chat.CartUser cartUser = cartUserRepository.save(new com.noah_solutions.entity.chat.CartUser("暫無簽名",v.getUserId(),null));
//                friendTypeRepository.save(new FriendType("我的好友",Integer.valueOf(cartUser.getCartUserId())));
            }
      });
    }

    @Override
    public List<Message> getOfflineMessage(String id) {
        List<Message>  messages = new ArrayList<>();
        friendMessageRepository.findAllByToUserIdAndIsRead(Integer.valueOf(id),0).forEach(v->{
            Message message = new Message();
            BeanUtils.copyNotNullProperties(v,message);
            message.setId(v.getFromUserId());
            message.setFrom((v.getFromUserId()));
            CartUser cartUser = cartUserRepository.selectUserById(v.getFromUserId()+"");
            if(cartUser==null)
                cartUser = cartUserRepository.selectAdminById(v.getFromUserId()+"");
            BeanUtils.copyNotNullProperties(cartUser,message);
            messages.add(message);
        });
        return messages;
    }


    @Override
    @Transactional
    public void markReaded(Integer id, Integer toId) {
        friendMessageRepository.markReaded(id,toId);
    }

    @Override
    @Transactional
    public void updateStatus(String cartUserId, Integer status) {
        cartUserRepository.updateStatus(cartUserId,status+"");
    }

    @Override
    @Transactional
    public void setOfflineAll() {
        cartUserRepository.setOfflineAll();
    }


    @Override
    @Transactional
    public void  addCart(com.noah_solutions.entity.chat.CartUser cartUser){
        if(!StringUtils.isEmpty(cartUser.getAdminId())){
            if(cartUserRepository.existsByAdminId(cartUser.getAdminId()))
                return;
        }
        if(!StringUtils.isEmpty(cartUser.getUserId())){
            if(cartUserRepository.existsByUserId(cartUser.getUserId()))
                return;
        }
        cartUser = cartUserRepository.save(cartUser);
        friendTypeRepository.save(new FriendType("我的好友",Integer.valueOf(cartUser.getCartUserId())));
    }

    @Override
    public CartUser getCartUserByUserId(String userId) {

        return  cartUserRepository.selectByUserId(userId);
    }
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}
