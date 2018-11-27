package com.springsecurity.core.controllers;

import com.springsecurity.core.dto.UserRegisterDTO;
import com.springsecurity.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(path = "/{id}/get-images", headers = "content-type=multipart/*")
    public List<Resource> getUserImages(@PathVariable Integer id) {
        return service.getUserImages(id);
    }

    @GetMapping(path = "/{id}/get-users")
    public Iterable<UserRegisterDTO> getUsers(@PathVariable Integer id) {
        return service.getUsers(id);
    }

    @GetMapping(path = "/{id}/get-user")
    public UserRegisterDTO getUser(@PathVariable Integer id) {
        return service.getUser(id);
    }

    @PostMapping(path = "/update-balance")
    public ResponseEntity<Integer> updateBalance(@RequestParam("balance") Integer balance) {
        return new ResponseEntity<>(service.updateBalance(balance));
    }
}
