package com.springsecurity.demo.repositories;

import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.entities.UserMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMessageRepository extends CrudRepository<UserMessage, Integer> {

    List<UserMessage> findByUserLikeAndReseiverIdLike(User user, Integer receiverId);
}
