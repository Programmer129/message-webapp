package com.springsecurity.core.controllers;

import com.springsecurity.core.dto.UserRegisterDTO;
import com.springsecurity.core.services.LoginService;
import com.springsecurity.core.services.RegisterService;
import com.springsecurity.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final UserService service;

    private final RegisterService registerService;

    private final LoginService loginService;

    @Autowired
    public UserController(UserService service, RegisterService registerService, LoginService loginService) {
        this.service = service;
        this.registerService = registerService;
        this.loginService = loginService;
    }

    @GetMapping(path = "/get-images", headers = "content-type=multipart/*")
    public List<Resource> getUserImages() {
        return service.getUserImages();
    }

    @GetMapping(path = "/get-users")
    public Iterable<UserRegisterDTO> getUsers() {
        return service.getUsers();
    }

    @GetMapping(path = "/get-user")
    public UserRegisterDTO getUser() {
        return service.getUser();
    }

    @PostMapping(path = "/upload", headers = "content-type=multipart/*")
    public UserRegisterDTO uploadFile(@RequestBody MultipartFile multipartFile) {
        return registerService.uploadFile(multipartFile);
    }

    @GetMapping(path = "/get-image", headers = "content-type=multipart/*")
    public Resource getUserProfile() {
        return loginService.userImg();
    }

    @PostMapping(path = "/update-balance")
    public ResponseEntity<Integer> updateBalance(@RequestParam("balance") Integer balance) {
        return new ResponseEntity<>(service.updateBalance(balance));
    }
}
