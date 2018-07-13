package com.springsecurity.demo.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Data
@Entity
@Table(name = "favourite_foods", schema = "demo")
public class FavouriteFoods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "foods_id")
    private Foods foods;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public FavouriteFoods() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouriteFoods that = (FavouriteFoods) o;
        return Objects.equals(foods, that.foods) &&
                Objects.equals(user, that.user);
    }
}
