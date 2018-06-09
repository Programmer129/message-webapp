package com.springsecurity.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getIsStock() {
        return isStock;
    }

    public void setIsStock(Integer isStock) {
        this.isStock = isStock;
    }

    public Integer getIsImported() {
        return isImported;
    }

    public void setIsImported(Integer isImported) {
        this.isImported = isImported;
    }

    public Integer getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Integer maxStock) {
        this.maxStock = maxStock;
    }
}
