package com.springsecurity.core.repositories;

import com.springsecurity.core.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u.userName FROM User u")
    List<String> findAllUserNames();

    User findByUserName(String userName);

    User findFirstByOrderByUserIdDesc();

    List<User> findUserByUserIdNotLike(Integer userId);

    User findByUserId(Integer id);
}
