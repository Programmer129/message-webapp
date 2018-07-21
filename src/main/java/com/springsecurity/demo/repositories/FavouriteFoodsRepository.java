package com.springsecurity.demo.repositories;

import com.springsecurity.demo.entities.FavouriteFood;
import com.springsecurity.demo.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavouriteFoodsRepository extends CrudRepository<FavouriteFood, Integer> {

    List<FavouriteFood> findAll();

    @Modifying
    @Query("update FavouriteFood f set f.amount=:amount where f.user=:user")
    void updateAmount(@Param("amount") int amount, @Param("user") User user);
}
