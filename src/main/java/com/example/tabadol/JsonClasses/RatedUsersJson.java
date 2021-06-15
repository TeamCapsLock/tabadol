package com.example.tabadol.JsonClasses;

import java.util.List;

public class RatedUsersJson {
    List<Long> ratedUsers;


    public RatedUsersJson(List<Long> ratedUsers) {
        this.ratedUsers = ratedUsers;
    }

    public RatedUsersJson() {
    }

    public List<Long> getRatedUsers() {
        return ratedUsers;
    }

    public void setRatedUsers(List<Long> ratedUsers) {
        this.ratedUsers = ratedUsers;
    }
}
