package com.springsecurity.core.repositories;

import com.springsecurity.core.entities.UserCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCardRepository extends CrudRepository<UserCard, Integer> {
}
