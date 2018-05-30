package com.springsecurity.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class UserMessageDTO {

    @JsonProperty("senderId")
    private int senderId;

    @JsonProperty("reseiverId")
    private int reseiverId;

    @JsonProperty("message")
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonProperty("sendDate")
    private Date sendDate;

    public UserMessageDTO() { }

    public int getReseiverId() {
        return reseiverId;
    }

    public void setReseiverId(int reseiverId) {
        this.reseiverId = reseiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "UserMessageDTO{" +
                "reseiverId=" + reseiverId +
                ", message='" + message + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}
