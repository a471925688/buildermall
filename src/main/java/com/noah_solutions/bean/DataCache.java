package com.noah_solutions.bean;

import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.User;

/**
 * 保存前端頁面跳轉需要的一些數據
 */
public class DataCache<T> {
    private T data;//數據
    private Long expirationTime;//過期時間
    private Integer useNum;//使用次數(-1為永久)

    private User user;//操作數據的賬戶
    private Admin admin;//操作數據的賬戶

    public DataCache(T data, Long expirationTime, Integer useNum) {
        this.data = data;
        this.expirationTime = expirationTime;
        this.useNum = useNum;
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

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }
}
