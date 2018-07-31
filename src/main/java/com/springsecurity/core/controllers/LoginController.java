package com.springsecurity.core.controllers;

import com.springsecurity.core.dto.UserDTO;
import com.springsecurity.core.dto.UserLoginDTO;
import com.springsecurity.core.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/token")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(path = "/auth")
    public UserLoginDTO getUserByName(@RequestBody UserLoginDTO loginDTO) {
        return loginService.authenticate(loginDTO);
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