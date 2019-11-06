package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noah_solutions.pojo.CartUser;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 2018 11 26 lijun
 * 建材网用户实体类
 */
@Entity
@Table(name = "user")
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class User implements Serializable {
    private static final long serialVersionUID = -1011381849582619990L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '用户id'")
    private String userId;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '登录id'" ,insertable=false,updatable=false)
    private String loginId;
    @Column(columnDefinition = "INT(2)  DEFAULT 0 COMMENT '是否禁用(1,是.0，否)'",insertable = false)
    private Boolean  userIsProhibit;
    @Column(columnDefinition = "VARCHAR(40) COMMENT '邮箱地址'")
    private String  userEmail;
    @Column(columnDefinition = "VARCHAR(50) NOT NULL COMMENT '姓名'")
    private String  userRealName;
    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '性别'")
    private String  userGender;
    @Column(columnDefinition = "VARCHAR(40)  COMMENT '电话'")
    private String  userPhone;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '地址'")
    private String  userAddress;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '头像访问地址'")
    private String  userHeadPortraitUrl;
//    private String sign;//签名
//    @Column(columnDefinition = "INT(2)  DEFAULT 1 COMMENT '在线状态'",insertable = false)
//    private String status;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  userCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  userUpdateTime;

    @NotFound(action= NotFoundAction.IGNORE)
    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="loginId")
    private Login login;

    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="userId")
    private List<ReceivingInfo> receivingInfos = new ArrayList<>();

    ////////////////////////////////////不映射到数据库中的属性 start ///////////////////////////////////////////////////////
    @Transient
    private CartUser cartUser;
    ////////////////////////////////////不映射到数据库中的属性 end///////////////////////////////////////////////////////

    public User() {

    }

    public CartUser getCartUser() {
        return cartUser;
    }

    public void setCartUser(CartUser cartUser) {
        this.cartUser = cartUser;
    }

//
//    public String getSign() {
//        return sign;
//    }
//
//    public void setSign(String sign) {
//        this.sign = sign;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public List<ReceivingInfo> getReceivingInfos() {
        return receivingInfos;
    }

    public void setReceivingInfos(List<ReceivingInfo> receivingInfos) {
        this.receivingInfos = receivingInfos;
    }

    public Boolean getUserIsProhibit() {
        return userIsProhibit;
    }

    public void setUserIsProhibit(Boolean userIsProhibit) {
        this.userIsProhibit = userIsProhibit;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserHeadPortraitUrl() {
        return userHeadPortraitUrl;
    }

    public void setUserHeadPortraitUrl(String userHeadPortraitUrl) {
        this.userHeadPortraitUrl = userHeadPortraitUrl;
    }

    public String getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(String userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    public String getUserUpdateTime() {
        return userUpdateTime;
    }

    public void setUserUpdateTime(String userUpdateTime) {
        this.userUpdateTime = userUpdateTime;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
