package com.noah_solutions.pojo;

import java.util.List;

/**
 * Created by pz on 16/11/23.
 */
public class Group {
    private Integer id;
    private String groupname;
    private String avatar;

    public Group() {
    }

    public Group(Integer id, String groupname, String avatar) {
        this.id = id;
        this.groupname = groupname;
        this.avatar = avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
