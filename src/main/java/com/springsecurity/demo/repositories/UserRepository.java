package com.springsecurity.demo.repositories;

import com.springsecurity.demo.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUserName(String userName);

    User findFirstByOrderByUserIdDesc();

    List<User> findByUserIdNotLike(Integer id);

    User findByUserId(Integer id);
}
