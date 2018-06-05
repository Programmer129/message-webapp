package com.springsecurity.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_card", schema = "demo")
public class UserCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "card_id")
    private String cardId;

    @NotBlank
    @Column(name = "card_type")
    private String cardType;

    @NotNull
    @Column(name = "date_added")
    private Date dateAdded;

    @NotNull
    @Column(name = "date_expired")
    private Date dateExpire;

    @Column(name = "balance")
    private BigDecimal balance;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public UserCard() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateExpire() {
        return dateExpire;
    }

    public void setDateExpire(Date dateExpire) {
        this.dateExpire = dateExpire;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCard userCard = (UserCard) o;
        return Objects.equals(id, userCard.id) &&
                Objects.equals(cardId, userCard.cardId) &&
                Objects.equals(cardType, userCard.cardType) &&
                Objects.equals(dateAdded, userCard.dateAdded) &&
                Objects.equals(dateExpire, userCard.dateExpire) &&
                Objects.equals(balance, userCard.balance) &&
                Objects.equals(user, userCard.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, cardId, cardType, dateAdded, dateExpire, balance, user);
    }

    @Override
    public String toString() {
        return "UserCard{" +
                "id=" + id +
                ", cardId='" + cardId + '\'' +
                ", cardType='" + cardType + '\'' +
                ", dateAdded=" + dateAdded +
                ", dateExpire=" + dateExpire +
                ", balance=" + balance +
                ", user=" + user +
                '}';
    }
}
