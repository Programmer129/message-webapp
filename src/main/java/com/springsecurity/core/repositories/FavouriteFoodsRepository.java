package com.springsecurity.core.repositories;

import com.springsecurity.core.entities.FavouriteFood;
import com.springsecurity.core.entities.User;
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
