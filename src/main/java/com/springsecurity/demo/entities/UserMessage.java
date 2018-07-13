package com.springsecurity.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "user_msg")
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private int messageId;

    @Column(name = "receiver_id")
    private int reseiverId;

    @Column(name = "message")
    private String message;

    @Column(name = "send_date")
    private Date sendDate;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public UserMessage() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMessage that = (UserMessage) o;
        return messageId == that.messageId &&
                reseiverId == that.reseiverId &&
                Objects.equals(message, that.message) &&
                Objects.equals(sendDate, that.sendDate) &&
                Objects.equals(user, that.user);
    }
}
