package com.springsecurity.demo.services;

import com.springsecurity.demo.configurations.MongoTemplateConfig;
import com.springsecurity.demo.dto.UserCardDTO;
import com.springsecurity.demo.dto.UserRegisterDTO;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.entities.UserCard;
import com.springsecurity.demo.repositories.UserCardRepository;
import com.springsecurity.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;

@Service
@Component
@Transactional
public class RegisterService {

    private final UserRepository userRepository;
    private final UserCardRepository cardRepository;
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoTemplateConfig.class);
    private GridFsOperations gridFsOperations = (GridFsOperations) context.getBean("gridFsTemplate");

    @Autowired
    public RegisterService(UserRepository userRepository, UserCardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    @Transactional
    public UserRegisterDTO saveUser(UserRegisterDTO userRegisterDTO) {
        User user = new User();

        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setUserName(userRegisterDTO.getUserName());
        user.setPassword(userRegisterDTO.getPassword());
        user.setBirthDate(userRegisterDTO.getBirthDate());
        user.setEmail(userRegisterDTO.getEmail());
        user.setIsActive(0);

        user = userRepository.save(user);

        UserCard userCard = new UserCard();
        UserCardDTO cardDTO = userRegisterDTO.getCardDTO();

        userCard.setCardId(cardDTO.getCardId());
        userCard.setCardType(cardDTO.getCardType());
        userCard.setDateAdded(cardDTO.getDateAdded());
        userCard.setDateExpire(cardDTO.getDateExpire());
        userCard.setBalance(new BigDecimal(cardDTO.getBalance()));
        userCard.setUser(user);

        userCard = cardRepository.save(userCard);

        cardDTO.setCardId(userCard.getCardId());
        cardDTO.setCardType(userCard.getCardType());
        cardDTO.setDateAdded(userCard.getDateAdded());
        cardDTO.setDateExpire(userCard.getDateExpire());
        cardDTO.setBalance(userCard.getBalance().doubleValue());

        userRegisterDTO.setFirstName(user.getFirstName());
        userRegisterDTO.setLastName(user.getLastName());
        userRegisterDTO.setUserName(user.getUserName());
        userRegisterDTO.setPassword(user.getPassword());
        userRegisterDTO.setBirthDate(user.getBirthDate());
        userRegisterDTO.setEmail(user.getEmail());
        userRegisterDTO.setCardDTO(cardDTO);

        return userRegisterDTO;
    }

    @Transactional
    public UserRegisterDTO updateUserImgId(String imgId) {
        User user = userRepository.findFirstByOrderByUserIdDesc();
        user.setImgId(imgId);

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setFirstName(user.getFirstName());
        userRegisterDTO.setLastName(user.getLastName());
        userRegisterDTO.setUserName(user.getUserName());
        userRegisterDTO.setPassword(user.getPassword());
        userRegisterDTO.setBirthDate(user.getBirthDate());
        userRegisterDTO.setEmail(user.getEmail());

        return userRegisterDTO;
    }

    private User lastRecord() {
        return userRepository.findFirstByOrderByUserIdDesc();
    }

    @Transactional
    public UserRegisterDTO uploadFile(MultipartFile file) {
        UserRegisterDTO user = new UserRegisterDTO();
        User user1 = lastRecord();
        String imgName = user1.getUserName();
        try {
            gridFsOperations.store(file.getInputStream(), "profile"+ imgName +".png", "image/png");
            String imgId = gridFsOperations.findOne(new Query().addCriteria(
                    Criteria.where("filename").is("profile"+ imgName +".png"))).getObjectId().toString();
            user = updateUserImgId(imgId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
