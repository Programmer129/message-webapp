package com.springsecurity.demo.services;

import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Objects;

@Component
@Service
@Transactional
public class UserService {

    private final UserRepository repository;
    private final HttpSession session;

    @Autowired
    public UserService(HttpSession session, UserRepository repository) {
        this.session = session;
        this.repository = repository;
    }

    @Transactional
    public Iterable<User> getUsers() {
        if(Objects.nonNull(session.getAttribute("id"))) {
            User user = repository.findByUserName(session.getAttribute("id").toString());
            return repository.findByUserIdNotLike(user.getUserId());
        }
        return new ArrayList<>();
    }
}
