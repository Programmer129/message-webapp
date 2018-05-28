package com.springsecurity.demo.controllers;

import com.springsecurity.demo.dto.UserMessageDTO;
import com.springsecurity.demo.services.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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

    @GetMapping(path = "/get-messages")
    public List<UserMessageDTO> getMessages() {
        return service.getMessages();
    }
}
