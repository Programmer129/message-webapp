package com.springsecurity.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private String category;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("isStock")
    private Integer isStock;

    @JsonProperty("isImported")
    private Integer isImported;

    @JsonProperty("maxStock")
    private Integer maxStock;
}
