package com.springsecurity.demo.controllers;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.springsecurity.demo.configurations.MongoTemplateConfig;
import com.springsecurity.demo.dto.UserLoginDTO;
import com.springsecurity.demo.dto.UserRegisterDTO;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoTemplateConfig.class);
    private GridFsOperations gridFsOperations = (GridFsOperations) context.getBean("gridFsTemplate");

    @Autowired
    public UserController(UserService userService, HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @PostMapping(path = "/auth")
    public UserLoginDTO getUserByName(@RequestBody UserLoginDTO user) {
        User result = this.userService.findByUserName(user);
        if(Objects.isNull(result)) {
            return new UserLoginDTO();
        }
        this.httpSession.setAttribute("id", true);
        this.httpSession.setMaxInactiveInterval(300000);
        UserLoginDTO userDTO = new UserLoginDTO();
        userDTO.setUserName(result.getUserName());
        userDTO.setPassword(result.getPassword());
        return userDTO;
    }

    @GetMapping(path = "/is-auth")
    public boolean isAuthenticated() {
        return Objects.nonNull(this.httpSession.getAttribute("id"));
    }

    @GetMapping(path = "/log-out")
    public ResponseEntity<HttpStatus> logOut() {
        if(Objects.nonNull(this.httpSession.getAttribute("id"))){
            this.httpSession.invalidate();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public UserRegisterDTO register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return userService.saveUser(userRegisterDTO);
    }

    @PostMapping(path = "/upload", headers = "content-type=multipart/*")
    public UserRegisterDTO uploadFile(@RequestBody MultipartFile multipartFile) {
        UserRegisterDTO user = new UserRegisterDTO();
        try {
            gridFsOperations.store(multipartFile.getInputStream(), "profile3.png", "image/png");
            user = userService.updateUserImgId(gridFsOperations.findOne(new Query().addCriteria(
                    Criteria.where("filename").is("profile3.png"))).getObjectId().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @GetMapping(path = "/get", headers = "content-type=multipart/mixed")
    public GridFSFile getUserProfile() {
        return gridFsOperations.findOne(new Query().addCriteria(Criteria.where("filename").is("profile.png")));
    }
}
