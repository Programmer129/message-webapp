package com.springsecurity.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "img_id")
    private String imgId;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "is_unread_msg")
    private Integer isUnreadMsg;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private UserRole role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserCard userCard;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserMessage> messages = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FavouriteFoods> favouriteFoods = new HashSet<>();

    public User(){}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", imgId='" + imgId + '\'' +
                ", isActive=" + isActive +
                ", isUnreadMsg=" + isUnreadMsg +
                ", role=" + role +
                ", userCard=" + userCard +
                ", messages=" + messages +
                ", favouriteFoods=" + favouriteFoods +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(birthDate, user.birthDate) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(imgId, user.imgId) &&
                Objects.equals(isActive, user.isActive) &&
                Objects.equals(isUnreadMsg, user.isUnreadMsg) &&
                Objects.equals(role, user.role) &&
                Objects.equals(userCard, user.userCard) &&
                Objects.equals(messages, user.messages) &&
                Objects.equals(favouriteFoods, user.favouriteFoods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, birthDate, userName, password, email, imgId, isActive, isUnreadMsg, role, userCard, messages, favouriteFoods);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Set<UserMessage> getMessages() {
        return messages;
    }

    public void setMessages(Set<UserMessage> messages) {
        this.messages = messages;
    }

    public Integer getIsUnreadMsg() {
        return isUnreadMsg;
    }

    public void setIsUnreadMsg(Integer isUnreadMsg) {
        this.isUnreadMsg = isUnreadMsg;
    }

    public UserCard getUserCard() {
        return userCard;
    }

    public void setUserCard(UserCard userCard) {
        this.userCard = userCard;
    }

    public Set<FavouriteFoods> getFavouriteFoods() {
        return favouriteFoods;
    }

    public void setFavouriteFoods(Set<FavouriteFoods> favouriteFoods) {
        this.favouriteFoods = favouriteFoods;
    }
}
