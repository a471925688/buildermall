package com.noah_solutions.pojo;

import java.util.List;

public class Friend {
    private int id;
    private String groupname;
    private List<CartUser> list;


    public Friend(int id, String groupname, List<CartUser> list) {
        this.id = id;
        this.groupname = groupname;
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public List<CartUser> getList() {
        return list;
    }

    public void setList(List<CartUser> list) {
        this.list = list;
    }
}
