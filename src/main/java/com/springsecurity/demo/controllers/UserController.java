package com.springsecurity.demo.controllers;

import com.mongodb.client.gridfs.GridFSBucket;
import com.springsecurity.demo.configurations.MongoTemplateConfig;
import com.springsecurity.demo.dto.UserLoginDTO;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.services.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoTemplateConfig.class);
    private GridFsOperations gridFsOperations = (GridFsOperations) context.getBean("gridFsTemplate");
    private GridFSBucket bucket = (GridFSBucket) context.getBean("getFSBucket");

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
        this.httpSession.setAttribute("id", user.getUserName());
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

    @GetMapping(path = "/get", headers = "content-type=multipart/*")
    public MultipartFile getUserProfile() {
        File file = new File("/home/levani/IdeaProjects/demo/src/main/resources/profile.png");
        try {
            bucket.downloadToStream("profile" + httpSession.getAttribute("id")+".png", new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FileItem fileItem = new DiskFileItem("profile.png","multipart/*", false, "profile.png",(int)file.length(), file);
        try {
            fileItem.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommonsMultipartFile(fileItem);
    }
}
