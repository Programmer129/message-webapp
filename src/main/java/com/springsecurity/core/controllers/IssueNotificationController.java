package com.springsecurity.core.controllers;

import com.springsecurity.core.dto.MailSendDTO;
import com.springsecurity.core.services.IssueNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api")
public class IssueNotificationController {

    @Autowired
    private IssueNotificationService issueNotificationService;

    @PostMapping(path = "/send-mail")
    public MailSendDTO sendMail(@RequestBody MailSendDTO sendDTO) throws MessagingException {
        return issueNotificationService.sendMail(sendDTO);
    }

}
