package com.noah_solutions.pojo;

import java.util.List;

public class CartInitData {
    private CartUser mine;
    private List<Friend> friend;
    private List<Group> group;




    public CartInitData(CartUser mine, List<Friend> friend, List<Group> group) {
        this.mine = mine;
        this.friend = friend;
        this.group = group;
    }

    public CartUser getMine() {
        return mine;
    }

    public void setMine(CartUser mine) {
        this.mine = mine;
    }

    public List<Friend> getFriend() {
        return friend;
    }

    public void setFriend(List<Friend> friend) {
        this.friend = friend;
    }

    public List<Group> getGroup() {
        return group;
    }

    public void setGroup(List<Group> group) {
        this.group = group;
    }
}
