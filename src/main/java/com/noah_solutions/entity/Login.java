package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 2018 11 26 lijun
 * 建材网登录表实体类
 */
@Entity
@Table(name = "login")
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Login implements Serializable {
    private static final long serialVersionUID = 6520992734289981509L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '登录id'")
    private String loginId;
    @Column(columnDefinition = "VARCHAR(50) NOT NULL COMMENT '(登录用户名)'")
    private String loginUserName;
    @Column(columnDefinition = "VARCHAR(60)  NOT NULL  COMMENT '登录密码'")
    private String  loginPassWord;

    @Column(columnDefinition = "INT(10) DEFAULT 0 COMMENT '登录次数'",insertable = false)
    private String  loginCount;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '最后登录时间'",updatable = false,insertable=false)
    private String  loginLastTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  loginUpdateTime;

//
//    @OneToOne(targetEntity = UserInfo.class)
//    @JoinColumn(name = "userId")
//    private UserInfo admin;


    public Login(String loginUserName, String loginPassWord,String loginId) {
        this.loginUserName = loginUserName;
        this.loginPassWord = loginPassWord;
        this.loginId = loginId;
    }
    public Login(String loginUserName, String loginPassWord) {
        this.loginUserName = loginUserName;
        this.loginPassWord = loginPassWord;
    }
    public Login() {

    }

    public Login(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }


    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getLoginPassWord() {
        return loginPassWord;
    }

    public void setLoginPassWord(String loginPassWord) {
        this.loginPassWord = loginPassWord;
    }

//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }

    public String getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(String loginCount) {
        this.loginCount = loginCount;
    }

    public String getLoginLastTime() {
        return loginLastTime;
    }

    public void setLoginLastTime(String loginLastTime) {
        this.loginLastTime = loginLastTime;
    }

    public String getLoginUpdateTime() {
        return loginUpdateTime;
    }

    public void setLoginUpdateTime(String loginUpdateTime) {
        this.loginUpdateTime = loginUpdateTime;
    }
}
