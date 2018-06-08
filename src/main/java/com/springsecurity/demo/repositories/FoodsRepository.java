package com.springsecurity.demo.repositories;

import com.springsecurity.demo.entities.Foods;
import org.springframework.data.repository.CrudRepository;

public interface FoodsRepository extends CrudRepository<Foods, Integer> {
}
