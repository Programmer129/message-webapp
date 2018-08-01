package com.springsecurity.core.configurations;

import com.springsecurity.core.entities.User;
import com.springsecurity.core.exceptions.UserNotFoundException;
import com.springsecurity.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class UserDetailsConfig implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserDetailsConfig(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = Optional
                .ofNullable(repository.findByUserName(s))
                .orElseThrow(UserNotFoundException::new);

        return new org.springframework.security.core.userdetails
                .User(user.getUserName(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_" + user.getRole()
                .getRoleName()));
    }
}
