package com.springsecurity.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {

    @JsonProperty("id")
    private int id;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("isUnreadMsg")
    private int isUnreadMsg;

    public UserDTO() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsUnreadMsg() {
        return isUnreadMsg;
    }

    public void setIsUnreadMsg(int isUnreadMsg) {
        this.isUnreadMsg = isUnreadMsg;
    }
}
