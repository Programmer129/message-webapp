package com.springsecurity.demo.services;

import com.springsecurity.demo.dto.UserMessageDTO;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.entities.UserMessage;
import com.springsecurity.demo.exceptions.UnauthorisedException;
import com.springsecurity.demo.repositories.UserMessageRepository;
import com.springsecurity.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        if(Objects.isNull(session.getAttribute("id"))) {
            throw new UnauthorisedException();
        }
        UserMessage message = new UserMessage();
        User user = userRepository.findByUserName(session.getAttribute("id").toString());
        User res = userRepository.findByUserId(messageDTO.getReseiverId());
        res.setIsUnreadMsg(1);
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
    public List<UserMessageDTO> getMessages(int id) {
        if(Objects.isNull(session.getAttribute("id"))) {
            throw new UnauthorisedException();
        }
        User user = userRepository.findByUserName(session.getAttribute("id").toString());
        user.setIsUnreadMsg(0);
        List<UserMessage> messages = messageRepository.findByUserLikeAndReseiverIdLike(user, id);
        Optional<User> byId = userRepository.findById(id);
        List<UserMessage> messages1 = messageRepository.findByUserLikeAndReseiverIdLike(byId.get(), user.getUserId());

        List<UserMessageDTO> res = messages.stream().map(this::mapToDTO).collect(Collectors.toList());
        res.addAll(messages1.stream().map(this::mapToDTO).collect(Collectors.toList()));

        return res.stream().sorted(Comparator.comparing(UserMessageDTO::getSendDate)).collect(Collectors.toList());
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
