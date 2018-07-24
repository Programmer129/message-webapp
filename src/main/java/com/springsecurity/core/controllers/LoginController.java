package com.springsecurity.core.controllers;

import com.springsecurity.core.dto.UserDTO;
import com.springsecurity.core.dto.UserLoginDTO;
import com.springsecurity.core.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(path = "/auth")
    public UserLoginDTO getUserByName() {
        return loginService.authenticate();
    }

    @GetMapping(path = "/is-auth")
    public boolean isAuthenticated() {
        return loginService.isAuthenticated();
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