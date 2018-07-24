package com.springsecurity.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
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
}
