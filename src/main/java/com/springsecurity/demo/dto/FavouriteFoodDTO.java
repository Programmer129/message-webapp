package com.springsecurity.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FavouriteFoodDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("foodId")
    private Integer foodId;

    @JsonProperty("amount")
    private Integer amount;
}
