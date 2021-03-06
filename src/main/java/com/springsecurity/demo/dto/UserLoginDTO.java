package com.springsecurity.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLoginDTO {

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;
}
