package com.springsecurity.core.services;

import com.mongodb.client.gridfs.GridFSBucket;
import com.springsecurity.core.configurations.JWTTokenProvider;
import com.springsecurity.core.configurations.MongoTemplateConfig;
import com.springsecurity.core.dto.UserDTO;
import com.springsecurity.core.dto.UserLoginDTO;
import com.springsecurity.core.entities.User;
import com.springsecurity.core.exceptions.UserNotFoundException;
import com.springsecurity.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@Component
@Transactional
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTTokenProvider tokenProvider;
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoTemplateConfig.class);
    private GridFSBucket bucket = (GridFSBucket) context.getBean("getFSBucket");

    @Autowired
    public LoginService(UserRepository userRepository, JWTTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public UserLoginDTO authenticate(UserLoginDTO loginDTO) {
        User result = Optional
                .ofNullable(this.userRepository.findByUserName(loginDTO.getUserName()))
                .orElseThrow(UserNotFoundException::new);
        result.setIsActive(1);
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.setUserName(result.getUserName());
        userDTO.setPassword(result.getPassword());
        userDTO.setToken(tokenProvider.generateToken(loginDTO));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));
        return userDTO;
    }

    @Transactional
    public Resource userImg() {
        File file = new File("/home/levani/IdeaProjects/core/src/main/resources/profile" + getCurrentUserName()+".png");
        Resource resource = null;
        try {
            bucket.downloadToStream("profile" + getCurrentUserName()+".png", new FileOutputStream(file));
            resource = new UrlResource(file.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resource;
    }

    public UserDTO getUser() {
        UserDTO dto = new UserDTO();
        User user = userRepository.findByUserName(getCurrentUserName());
        dto.setUserName(user.getUserName());
        dto.setId(user.getUserId());
        dto.setIsUnreadMsg(user.getIsUnreadMsg());
        dto.setBalance(user.getUserCard().getBalance().doubleValue());

        return dto;
    }

    public boolean isAuthenticated() {
        System.out.println(getCurrentUserName());
        return !Objects.equals(getCurrentUserName(), "anonymousUser");
    }

    private String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
