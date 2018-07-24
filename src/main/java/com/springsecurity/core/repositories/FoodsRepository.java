package com.springsecurity.core.repositories;

import com.springsecurity.core.entities.Food;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FoodsRepository extends CrudRepository<Food, Integer> {

    List<Food> findAll();

    Food findByName(String name);

    List<Food> findByNameStartingWith(String name);
}
