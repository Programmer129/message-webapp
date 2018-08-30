package com.springsecurity.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MailSendDTO {

    @JsonProperty("to")
    private String recipient;

    @JsonProperty("from")
    private String sender;

    @JsonProperty("host")
    private String host;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("text")
    private String text;

    @JsonProperty("password")
    private String password;
}
