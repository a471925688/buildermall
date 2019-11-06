package com.noah_solutions.entity.chat;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Entity
public class FriendType {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分组名
     */
    private String typeName;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 创建时间
     */
    @Column(insertable = false)
    private String buildTime;

    /**
     * 是否为默认分组：1为默认，0为不是默认分组
     */
    private Integer isDefault;

    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="typeId",insertable = false,updatable = false)
    private List<Friend> friends; // 分组下的好友list

    @Transient
    private int friendCount; // 分组下好友总数
    @Transient
    private int onlineCount; // 在线好友数

    public FriendType() {
    }

    public FriendType(String typeName, Integer userId) {
        this.typeName = typeName;
        this.userId = userId;
    }

    public int getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(int friendCount) {
        this.friendCount = friendCount;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
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
     * 获取分组名
     *
     * @return type_name - 分组名
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 设置分组名
     *
     * @param typeName 分组名
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取创建时间
     *
     * @return build_time - 创建时间
     */
    public String getBuildTime() {
        return buildTime;
    }

    /**
     * 设置创建时间
     *
     * @param buildTime 创建时间
     */
    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    /**
     * 获取是否为默认分组：1为默认，0为不是默认分组
     *
     * @return is_default - 是否为默认分组：1为默认，0为不是默认分组
     */
    public Integer getIsDefault() {
        return isDefault;
    }

    /**
     * 设置是否为默认分组：1为默认，0为不是默认分组
     *
     * @param isDefault 是否为默认分组：1为默认，0为不是默认分组
     */
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}