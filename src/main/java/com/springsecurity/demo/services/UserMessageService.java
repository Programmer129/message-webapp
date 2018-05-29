package com.springsecurity.demo.services;

import com.springsecurity.demo.dto.UserMessageDTO;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.entities.UserMessage;
import com.springsecurity.demo.repositories.UserMessageRepository;
import com.springsecurity.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
@Transactional
public class UserMessageService {

    private final UserRepository userRepository;
    private final UserMessageRepository messageRepository;
    private final HttpSession session;

    @Autowired
    public UserMessageService(UserMessageRepository messageRepository, HttpSession session, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.session = session;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserMessageDTO sendMessage(UserMessageDTO messageDTO) {
        UserMessage message = new UserMessage();
        User user = userRepository.findByUserName(session.getAttribute("id").toString());
        message.setUser(user);
        message.setReseiverId(messageDTO.getReseiverId());
        message.setMessage(messageDTO.getMessage());
        message.setSendDate(messageDTO.getSendDate());

        UserMessage save = messageRepository.save(message);

        messageDTO.setReseiverId(save.getReseiverId());
        messageDTO.setMessage(save.getMessage());
        messageDTO.setSendDate(save.getSendDate());

        return messageDTO;
    }

    @Transactional
    public List<UserMessageDTO> getMessages() {
        User user = userRepository.findByUserName(session.getAttribute("id").toString());
        List<UserMessage> messages = messageRepository.findByReseiverIdLike(user.getUserId());

        return messages.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private UserMessageDTO mapToDTO(UserMessage message) {
        UserMessageDTO messageDTO = new UserMessageDTO();
        messageDTO.setReseiverId(message.getReseiverId());
        messageDTO.setSendDate(message.getSendDate());
        messageDTO.setMessage(message.getMessage());
        messageDTO.setSenderId(message.getUser().getUserId());

        return messageDTO;
    }
}
