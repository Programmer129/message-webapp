package com.springsecurity.core.repositories;

import com.springsecurity.core.entities.User;
import com.springsecurity.core.entities.UserMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMessageRepository extends CrudRepository<UserMessage, Integer> {

    List<UserMessage> findUserMessageByReseiverId(Integer id);

    List<UserMessage> findUserMessageByUser(User user);
}
