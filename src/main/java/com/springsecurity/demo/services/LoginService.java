package com.springsecurity.demo.services;

import com.mongodb.client.gridfs.GridFSBucket;
import com.springsecurity.demo.configurations.MongoTemplateConfig;
import com.springsecurity.demo.dto.UserDTO;
import com.springsecurity.demo.dto.UserLoginDTO;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@Component
@Transactional
public class LoginService {

    private final UserRepository userRepository;
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoTemplateConfig.class);
    private GridFSBucket bucket = (GridFSBucket) context.getBean("getFSBucket");

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserLoginDTO authenticate(UserLoginDTO loginDTO) {
        User result = this.userRepository.findByUserName(loginDTO.getUserName());
        if(Objects.isNull(result) || !result.getPassword().equals(loginDTO.getPassword())) {
            return new UserLoginDTO();
        }
        result.setIsActive(1);
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.setUserName(result.getUserName());
        userDTO.setPassword(result.getPassword());
        return userDTO;
    }

    @Transactional
    public Resource userImg() {
        File file = new File("/home/levani/IdeaProjects/demo/src/main/resources/profile" + getCurrentUserName()+".png");
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
        return Objects.nonNull(getCurrentUserName());
    }

    @Transactional
    public void logOut() {
        User result = this.userRepository.findByUserName(getCurrentUserName());
        result.setIsActive(0);
    }

    private String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
