package com.springsecurity.demo.repositories;

import com.springsecurity.demo.entities.UserCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCardRepository extends CrudRepository<UserCard, Integer> {
}
