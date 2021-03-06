package com.springsecurity.demo.controllers;

import com.springsecurity.demo.dto.UserRegisterDTO;
import com.springsecurity.demo.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api")
public class RegisterController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping(path = "/register")
    public UserRegisterDTO register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        //TODO add password hashing fix json ignore
        return registerService.saveUser(userRegisterDTO);
    }

    @PostMapping(path = "/upload", headers = "content-type=multipart/*")
    public UserRegisterDTO uploadFile(@RequestBody MultipartFile multipartFile) {
        return registerService.uploadFile(multipartFile);
    }
}
