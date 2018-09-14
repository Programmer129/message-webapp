package com.springsecurity.core.controllers;

import com.springsecurity.core.dto.UserMessageDTO;
import com.springsecurity.core.services.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api")
public class UserMessageController {

    private final UserMessageService service;

    @Autowired
    public UserMessageController(UserMessageService service) {
        this.service = service;
    }

    @PostMapping(path = "/send")
    public UserMessageDTO sendMessage(@RequestBody UserMessageDTO messageDTO) {
        return service.sendMessage(messageDTO);
    }

    @GetMapping(path = "/get-messages/{id}")
    public List<UserMessageDTO> getMessages(@PathVariable int id) {
        return service.getMessages(id);
    }
}
