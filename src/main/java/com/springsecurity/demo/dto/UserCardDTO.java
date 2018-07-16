package com.springsecurity.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UserCardDTO {

    @NotBlank
    @JsonProperty("cardId")
    private String cardId;

    @JsonProperty("cardType")
    private String cardType;

    @JsonProperty("dateAdded")
    private Date dateAdded;

    @JsonProperty("dateExpire")
    private Date dateExpire;

    @JsonProperty("balance")
    private Double balance;
}
