package com.springsecurity.demo.services;

import com.springsecurity.demo.dto.UserCardDTO;
import com.springsecurity.demo.dto.UserRegisterDTO;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.exceptions.UnauthorisedException;
import com.springsecurity.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Service
@Transactional
public class UserService {

    private final UserRepository repository;
    private final HttpSession session;

    @Autowired
    public UserService(HttpSession session, UserRepository repository) {
        this.session = session;
        this.repository = repository;
    }

    @Transactional
    public Iterable<UserRegisterDTO> getUsers() {
        if (Objects.isNull(session.getAttribute("id"))) {
            throw new UnauthorisedException();
        }
        User user = repository.findByUserName(session.getAttribute("id").toString());
        List<User> list = repository.findByUserIdNotLike(user.getUserId());
        return list.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public UserRegisterDTO getUser() {
        if (Objects.isNull(session.getAttribute("id"))) {
            throw new UnauthorisedException();
        }
        User user = repository.findByUserName(session.getAttribute("id").toString());

        return mapToDTO(user);
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
}
