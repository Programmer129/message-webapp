package com.springsecurity.demo.services;

import com.mongodb.client.gridfs.GridFSBucket;
import com.springsecurity.demo.configurations.MongoTemplateConfig;
import com.springsecurity.demo.dto.UserCardDTO;
import com.springsecurity.demo.dto.UserRegisterDTO;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
@Transactional
public class UserService {

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoTemplateConfig.class);
    private GridFSBucket bucket = (GridFSBucket) context.getBean("getFSBucket");
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Iterable<UserRegisterDTO> getUsers() {
        System.out.println(getCurrentUserName());
        User user = repository.findByUserName(getCurrentUserName());
        List<User> list = repository.findByUserIdNotLike(user.getUserId());
        return list.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public UserRegisterDTO getUser() {
        User user = repository.findByUserName(getCurrentUserName());

        return mapToDTO(user);
    }

    private String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private UserRegisterDTO mapToDTO(User user) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        UserCardDTO cardDTO = new UserCardDTO();

        cardDTO.setCardType(user.getUserCard().getCardType());
        cardDTO.setCardId(user.getUserCard().getCardId());
        cardDTO.setDateAdded(user.getUserCard().getDateAdded());
        cardDTO.setDateExpire(user.getUserCard().getDateExpire());
        cardDTO.setBalance(user.getUserCard().getBalance().doubleValue());

        userRegisterDTO.setFirstName(user.getFirstName());
        userRegisterDTO.setLastName(user.getLastName());
        userRegisterDTO.setEmail(user.getEmail());
        userRegisterDTO.setBirthDate(user.getBirthDate());
        userRegisterDTO.setIsActive(user.getIsActive());
        userRegisterDTO.setUserName(user.getUserName());
        userRegisterDTO.setUserId(user.getUserId());
        userRegisterDTO.setCardDTO(cardDTO);

        return userRegisterDTO;
    }

    @Transactional
    public HttpStatus updateBalance(Integer balance) {
        User user = repository.findByUserName(getCurrentUserName());
        user.getUserCard().setBalance(BigDecimal.valueOf(balance));

        return HttpStatus.OK;
    }

    public List<Resource> getUserImages() {
        User user = repository.findByUserName(getCurrentUserName());
        List<User> list = repository.findByUserIdNotLike(user.getUserId());
        List<Resource> resources = new ArrayList<>();
        for (User user1 : list) {
            File file = new File("/home/levani/IdeaProjects/demo/src/main/resources/profile" + user1.getUserName()+".png");
            try {
                bucket.downloadToStream("profile" + user1.getUserName()+".png", new FileOutputStream(file));
                resources.add(new UrlResource(file.toURI()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resources;
    }
}
