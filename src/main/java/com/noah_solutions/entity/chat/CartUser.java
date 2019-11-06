package com.noah_solutions.entity.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Entity
public class CartUser {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String cartUserId;

    private String  sign;//签名
    @Column(columnDefinition = "INT(4)  DEFAULT 1 COMMENT '在线状态'",insertable = false)
    private String  status;
    @Column(columnDefinition = "INT(10) DEFAULT 1 COMMENT '用户id'")
    private String  userId;
    @Column(columnDefinition = "INT(10) DEFAULT 1 COMMENT '管理员id'")
    private String  adminId;
    private String  address;
    private String  phone;
    private String  gender;


    /**
     * 好友用户
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="userId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private User user;


    /**
     * 好友用户
     */
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="adminId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private Admin admin;


    public CartUser() {
    }

    public CartUser(String sign, String userId, String adminId) {
        this.sign = sign;
        this.userId = userId;
        this.adminId = adminId;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCartUserId() {
        return cartUserId;
    }

    public void setCartUserId(String cartUserId) {
        this.cartUserId = cartUserId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
