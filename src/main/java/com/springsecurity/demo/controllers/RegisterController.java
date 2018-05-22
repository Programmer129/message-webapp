package com.springsecurity.demo.controllers;

import com.springsecurity.demo.configurations.MongoTemplateConfig;
import com.springsecurity.demo.dto.UserRegisterDTO;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api")
public class RegisterController {

    private final UserService userService;
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoTemplateConfig.class);
    private GridFsOperations gridFsOperations = (GridFsOperations) context.getBean("gridFsTemplate");

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public UserRegisterDTO register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.saveUser(userRegisterDTO);
    }

    @PostMapping(path = "/upload", headers = "content-type=multipart/*")
    public UserRegisterDTO uploadFile(@RequestBody MultipartFile multipartFile) {
        UserRegisterDTO user = new UserRegisterDTO();
        User user1 = userService.lastRecord();
        String imgName = user1.getUserName();
        try {
            gridFsOperations.store(multipartFile.getInputStream(), "profile"+ imgName +".png", "image/png");
            user = userService.updateUserImgId(gridFsOperations.findOne(new Query().addCriteria(
                    Criteria.where("filename").is("profile"+ imgName +".png"))).getObjectId().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

}
