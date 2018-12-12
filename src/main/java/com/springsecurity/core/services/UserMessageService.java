package com.springsecurity.core.services;

import com.springsecurity.core.dto.UserMessageDTO;
import com.springsecurity.core.entities.User;
import com.springsecurity.core.entities.UserMessage;
import com.springsecurity.core.repositories.UserMessageRepository;
import com.springsecurity.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
@Transactional
public class UserMessageService {

    private final UserRepository userRepository;
    private final UserMessageRepository messageRepository;
    private final OneSignalService oneSignalService;

    @Autowired
    public UserMessageService(UserMessageRepository messageRepository,
                              UserRepository userRepository,
                              OneSignalService oneSignalService) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.oneSignalService = oneSignalService;
    }

    @Transactional
    public UserMessageDTO sendMessage(UserMessageDTO messageDTO) {
        UserMessage message = new UserMessage();
        User user = userRepository.findByUserId(messageDTO.getSenderId());
        User res = userRepository.findByUserId(messageDTO.getReseiverId());
        res.setIsUnreadMsg(1);
        message.setUser(user);
        message.setReseiverId(messageDTO.getReseiverId());
        message.setMessage(messageDTO.getMessage());
        message.setSendDate(new Date());

        UserMessage save = messageRepository.save(message);

        messageDTO.setReseiverId(save.getReseiverId());
        messageDTO.setMessage(save.getMessage());
        messageDTO.setSendDate(save.getSendDate());

        oneSignalService.sendNotification(user.getUserName(), save.getMessage());

        return messageDTO;
    }

    @Transactional
    public List<UserMessageDTO> getMessages(int id) {
        User user = userRepository.findByUserId(id);

        List<UserMessage> messages = messageRepository.findUserMessageByReseiverId(id);
        messages.addAll(messageRepository.findUserMessageByUser(user));

        messages.sort(Comparator.comparing(UserMessage::getSendDate));

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
