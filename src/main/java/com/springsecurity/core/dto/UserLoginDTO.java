package com.springsecurity.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLoginDTO {

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("password")
    private String password;

    @JsonProperty("token")
    private String token;
}
