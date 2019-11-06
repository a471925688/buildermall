package com.noah_solutions.entity.chat;

import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Entity
public class Friend {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 好友id
     */
    private String friendId;

    /**
     * 建立时间
     */
    @Column(insertable = false)
    private String buildTime;

    /**
     * 好友分组id
     */
    private Integer typeId;

    @Transient
    private List<User> users;


    /**
     * 好友用户
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="friendId",insertable = false,updatable = false)
    private CartUser cartFriend;


    public Friend() {
    }

    public Friend(String userId, String friendId, Integer typeId) {
        this.userId = userId;
        this.friendId = friendId;
        this.typeId = typeId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public CartUser getCartFriend() {
        return cartFriend;
    }

    public void setCartFriend(CartUser cartFriend) {
        this.cartFriend = cartFriend;
    }

    /**
     * 获取自增主键
     *
     * @return id - 自增主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增主键
     *
     * @param id 自增主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取好友id
     *
     * @return friend_id - 好友id
     */
    public String getFriendId() {
        return friendId;
    }

    /**
     * 设置好友id
     *
     * @param friendId 好友id
     */
    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    /**
     * 获取建立时间
     *
     * @return build_time - 建立时间
     */
    public String getBuildTime() {
        return buildTime;
    }

    /**
     * 设置建立时间
     *
     * @param buildTime 建立时间
     */
    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    /**
     * 获取好友分组id
     *
     * @return type_id - 好友分组id
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 设置好友分组id
     *
     * @param typeId 好友分组id
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}