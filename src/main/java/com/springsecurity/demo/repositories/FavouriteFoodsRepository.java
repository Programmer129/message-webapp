package com.springsecurity.demo.repositories;

import com.springsecurity.demo.entities.FavouriteFoods;
import org.springframework.data.repository.CrudRepository;

public interface FavouriteFoodsRepository extends CrudRepository<FavouriteFoods, Integer> {
}
