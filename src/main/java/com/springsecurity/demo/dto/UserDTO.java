package com.springsecurity.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("isUnreadMsg")
    private int isUnreadMsg;

    @JsonProperty("balance")
    private Double balance;
}
