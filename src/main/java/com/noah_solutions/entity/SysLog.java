package com.noah_solutions.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "sys_log")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class SysLog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '登录id'")
    private String logId;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String logTime;		// 创建时间
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '模块描述'")
    private String logContent;		// 日志描述内容，业务组装
    @Column(columnDefinition = "VARCHAR(100) COMMENT '控制器'")
    private String logController;	// 操作方法
    @Column(columnDefinition = "VARCHAR(100) COMMENT '方法'")
    private String logMethod;	// 操作方法
    @Column(columnDefinition = "VARCHAR(60) COMMENT '操作人名称'")
    private String logUserName;	//
    @Column(columnDefinition = "INT(10)   COMMENT '操作人ID'")
    private String logUserId;		//
    @Column(columnDefinition = "INT(10)   COMMENT '操作人类型(1.管理员 2.会员)'")
    private Integer logUserType;
    @Column(columnDefinition = "VARCHAR(60) COMMENT '模块编码'")
    private String logModule;		//
    @Column(columnDefinition = "TEXT COMMENT '参数：JSON格式'")
    private String logParam;		// 参数：JSON格式
    @Column(columnDefinition = "INT(2) NOT NULL COMMENT '(状态：0成功1异常)'")
    private Integer logState;	// 状态：0成功1异常
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '异常内容'")
    private String logEx;
//    private String logType;	// 0:服务器 1： apk
//    private String logVersion;	// 版本号
    @Column(columnDefinition = "TEXT COMMENT '日志JSON'")
    private String logJson;	// 日志JSON
//    private Blob logIsFormat; //是否格式化数据
//    private String tokenId;
    @Column(columnDefinition = "VARCHAR(80) COMMENT 'ip'")
    private String logIp;

    @Transient
    private String logAddr;

    public SysLog() {
    }

    public String getLogAddr() {
//        if(!logIp.equals("localhost1")){
//            try {
//                return AddressUtils.getAddresses("113.74.126.91");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        return "内网ip";
        return logAddr;
    }

    public void setLogAddr(String logAddr) {
        this.logAddr = logAddr;
    }

    public Integer getLogUserType() {
        return logUserType;
    }

    public void setLogUserType(Integer logUserType) {
        this.logUserType = logUserType;
    }

    public String getLogController() {
        return logController;
    }

    public void setLogController(String logController) {
        this.logController = logController;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getLogUserId() {
        return logUserId;
    }

    public void setLogUserId(String logUserId) {
        this.logUserId = logUserId;
    }

    public Integer getLogState() {
        return logState;
    }

    public void setLogState(Integer logState) {
        this.logState = logState;
    }

    public String getLogMethod() {
        return logMethod;
    }

    public void setLogMethod(String logMethod) {
        this.logMethod = logMethod;
    }

    public String getLogUserName() {
        return logUserName;
    }

    public void setLogUserName(String logUserName) {
        this.logUserName = logUserName;
    }



    public String getLogModule() {
        return logModule;
    }

    public void setLogModule(String logModule) {
        this.logModule = logModule;
    }


    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getLogParam() {
        return logParam;
    }

    public void setLogParam(String logParam) {
        this.logParam = logParam;
    }

    public String getLogEx() {
        return logEx;
    }

    public void setLogEx(String logEx) {
        this.logEx = logEx;
    }

    public String getLogJson() {
        return logJson;
    }

    public void setLogJson(String logJson) {
        this.logJson = logJson;
    }

    public String getLogIp() {
        return logIp;
    }

    public void setLogIp(String logIp) {
        this.logIp = logIp;
    }
}
