package com.springsecurity.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRegisterDTO {

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("birthDate")
    private String birthDate;

    @JsonProperty("email")
    private String email;

    @JsonProperty("isActive")
    private Integer isActive;

    @JsonProperty("userCard")
    private UserCardDTO cardDTO;
}
