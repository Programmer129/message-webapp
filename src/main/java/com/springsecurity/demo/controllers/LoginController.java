package com.springsecurity.demo.controllers;

import com.springsecurity.demo.dto.UserDTO;
import com.springsecurity.demo.dto.UserLoginDTO;
import com.springsecurity.demo.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(path = "/auth")
    public UserLoginDTO getUserByName(@RequestBody UserLoginDTO user) {
        return loginService.authenticate(user);
    }

    @GetMapping(path = "/is-auth")
    public boolean isAuthenticated() {
        return loginService.isAuthenticated();
    }

    @GetMapping(path = "/log-out")
    public ResponseEntity<HttpStatus> logOut() {
        if(loginService.isAuthenticated()){
            loginService.logOut();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/get-image", headers = "content-type=multipart/*")
    public Resource getUserProfile() {
        return loginService.userImg();
    }

    @GetMapping(path = "/get-name")
    public UserDTO getUserName() {
        return loginService.getUser();
    }
}