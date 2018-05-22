package com.springsecurity.demo.services;

import com.springsecurity.demo.dto.UserLoginDTO;
import com.springsecurity.demo.dto.UserRegisterDTO;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class UserService {

    private static final int ROLE_ID = 2;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User findByUserName(UserLoginDTO userDTO) {
        User user = this.userRepository.findByUserName(userDTO.getUserName());
        if(Objects.nonNull(user) && user.getPassword().equals(userDTO.getPassword())) {
            return user;
        }
        return null;
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

        user = userRepository.save(user);

        userRegisterDTO.setFirstName(user.getFirstName());
        userRegisterDTO.setLastName(user.getLastName());
        userRegisterDTO.setUserName(user.getUserName());
        userRegisterDTO.setPassword(user.getPassword());
        userRegisterDTO.setBirthDate(user.getBirthDate());
        userRegisterDTO.setEmail(user.getEmail());

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

    @Transactional
    public User lastRecord() {
        return userRepository.findFirstByOrderByUserIdDesc();
    }
}
