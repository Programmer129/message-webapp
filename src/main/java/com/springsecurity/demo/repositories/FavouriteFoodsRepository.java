package com.springsecurity.demo.repositories;

import com.springsecurity.demo.entities.FavouriteFoods;
import com.springsecurity.demo.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavouriteFoodsRepository extends CrudRepository<FavouriteFoods, Integer> {

    List<FavouriteFoods> findByUser(User user);
}
