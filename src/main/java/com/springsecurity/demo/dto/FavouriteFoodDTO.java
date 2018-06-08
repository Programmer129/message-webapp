package com.springsecurity.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FavouriteFoodDTO {

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("foodId")
    private Integer foodId;

    @JsonProperty("amount")
    private Integer amount;

    public FavouriteFoodDTO() { }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "FavouriteFoodDTO{" +
                "userId=" + userId +
                ", foodId=" + foodId +
                ", amount=" + amount +
                '}';
    }
}
