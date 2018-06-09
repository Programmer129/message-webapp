package com.springsecurity.demo.repositories;

import com.springsecurity.demo.entities.Foods;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FoodsRepository extends CrudRepository<Foods, Integer> {

    List<Foods> findAll();
}
