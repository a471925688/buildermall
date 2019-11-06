package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.pojo.CartUser;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 2018 11 26 lijun
 * 建材网管理员实体类
 */
@Entity
@Table(name = "tb_admin")
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Admin implements Serializable {
    private static final long serialVersionUID = -1011381849582619990L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '管理员id'")
    private String adminId;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '管理员id'" ,insertable=false,updatable=false)
    private String roleId;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '登录id'" ,insertable=false,updatable=false)
    private String loginId;
    @Column(columnDefinition = "INT(2) NOT NULL COMMENT '(管理员类型.1.管理员，2.供应商)'")
    private Integer adminType;
    @Column(columnDefinition = "INT(2)  DEFAULT 0 COMMENT '是否禁用(1,是.0，否)'",insertable = false)
    private Boolean  adminIsProhibit;
    @Column(columnDefinition = "VARCHAR(40) COMMENT '邮箱地址'")
    private String  adminEmail;
    @Column(columnDefinition = "VARCHAR(50) NOT NULL COMMENT '姓名'")
    private String  adminRealName;
    @Column(columnDefinition = "VARCHAR(15) NOT NULL COMMENT '性别'")
    private String  adminGender;
    @Column(columnDefinition = "VARCHAR(40)  COMMENT '电话'")
    private String  adminPhone;

    @Column(columnDefinition = "VARCHAR(100)  COMMENT '地址'")
    private String  adminAddress;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '头像访问地址'")
    private String  adminHeadPortraitUrl;
//    private String sign;//签名
//    @Column(columnDefinition = "INT(2)  DEFAULT 1 COMMENT '在线状态'",insertable = false)
//    private String status;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  adminCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  adminUpdateTime;





    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="loginId")
    private Login login;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH })
    @JoinColumn(name="roleId")
    private Role role;

    ////////////////////////////////////不映射到数据库中的属性 start ///////////////////////////////////////////////////////
    @Transient
    private String roleName;
    @Transient
    private String avatar;//头像

    @Transient
    private CartUser cartUser;
    ////////////////////////////////////不映射到数据库中的属性 end///////////////////////////////////////////////////////

    public Admin(String adminId,String roleId,  Integer adminType, Boolean adminIsProhibit, String adminEmail, String adminRealName, String adminGender, String adminPhone, String adminAddress, String adminHeadPortraitUrl, String adminCreateTime,String roleName,Login login) {
        this.adminId = adminId;
        this.roleId = roleId;
        this.adminType = adminType;
        this.adminIsProhibit = adminIsProhibit;
        this.adminEmail = adminEmail;
        this.adminRealName = adminRealName;
        this.adminGender = adminGender;
        this.adminPhone = adminPhone;
        this.adminAddress = adminAddress;
        this.adminHeadPortraitUrl = adminHeadPortraitUrl;
        this.adminCreateTime = adminCreateTime;
        this.roleName = roleName;
        this.login=login;
    }

    public Admin(String adminId,Role role, Login login, Integer adminType,  String adminEmail, String adminRealName, String adminGender, String adminPhone, String adminAddress, String adminHeadPortraitUrl) {
        this.adminId = adminId;
        this.role = role;
        this.login = login;
        this.adminType = adminType;
        this.adminEmail = adminEmail;
        this.adminRealName = adminRealName;
        this.adminGender = adminGender;
        this.adminPhone = adminPhone;
        this.adminAddress = adminAddress;
        this.adminHeadPortraitUrl = adminHeadPortraitUrl;
    }

    public Admin() {

    }

    public CartUser getCartUser() {
        return cartUser;
    }

    public void setCartUser(CartUser cartUser) {
        this.cartUser = cartUser;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

//    public String getSign() {
//        return sign;
//    }
//
//    public void setSign(String sign) {
//        this.sign = sign;
//    }
//
//    public String getRoleName() {
//        return roleName;
//    }
//
//    public void setRoleName(String roleName) {
//        this.roleName = roleName;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public String getAvatar() {
        if(!StringUtils.isEmpty(adminHeadPortraitUrl))
            return ProjectConfig.HOST_NAME+"image/"+adminHeadPortraitUrl;
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getAdminIsProhibit() {
        return adminIsProhibit;
    }

    public void setAdminIsProhibit(Boolean adminIsProhibit) {
        this.adminIsProhibit = adminIsProhibit;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAdminGender() {
        return adminGender;
    }

    public void setAdminGender(String adminGender) {
        this.adminGender = adminGender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Integer getAdminType() {
        return adminType;
    }

    public void setAdminType(Integer adminType) {
        this.adminType = adminType;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminRealName() {
        return adminRealName;
    }

    public void setAdminRealName(String adminRealName) {
        this.adminRealName = adminRealName;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminAddress() {
        return adminAddress;
    }

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public String getAdminHeadPortraitUrl() {
//        if(StringUtils.isEmpty(adminHeadPortraitUrl))
//            adminHeadPortraitUrl = "http://tva3.sinaimg.cn/crop.0.0.180.180.180/7f5f6861jw1e8qgp5bmzyj2050050aa8.jpg";
        return adminHeadPortraitUrl;
    }

    public void setAdminHeadPortraitUrl(String adminHeadPortraitUrl) {
        this.adminHeadPortraitUrl = adminHeadPortraitUrl;
    }

    public String getAdminCreateTime() {
        return adminCreateTime;
    }

    public void setAdminCreateTime(String adminCreateTime) {
        this.adminCreateTime = adminCreateTime;
    }

    public String getAdminUpdateTime() {
        return adminUpdateTime;
    }

    public void setAdminUpdateTime(String adminUpdateTime) {
        this.adminUpdateTime = adminUpdateTime;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
