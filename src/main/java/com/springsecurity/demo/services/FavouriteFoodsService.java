package com.springsecurity.demo.services;

import com.springsecurity.demo.dto.FavouriteFoodDTO;
import com.springsecurity.demo.entities.FavouriteFoods;
import com.springsecurity.demo.entities.Foods;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.exceptions.UnauthorisedException;
import com.springsecurity.demo.repositories.FavouriteFoodsRepository;
import com.springsecurity.demo.repositories.FoodsRepository;
import com.springsecurity.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Service
@Component
@Transactional
public class FavouriteFoodsService {

    private final HttpSession session;
    private final FavouriteFoodsRepository favouriteFoodsRepository;
    private final UserRepository userRepository;
    private final FoodsRepository foodsRepository;

    @Autowired
    public FavouriteFoodsService(HttpSession session, FavouriteFoodsRepository favouriteFoodsRepository, UserRepository userRepository, FoodsRepository foodsRepository) {
        this.session = session;
        this.favouriteFoodsRepository = favouriteFoodsRepository;
        this.userRepository = userRepository;
        this.foodsRepository = foodsRepository;
    }

    @Transactional
    public FavouriteFoodDTO addToFavourite(FavouriteFoodDTO foodDTO) {
        if(Objects.isNull(session.getAttribute("id"))) {
            throw new UnauthorisedException();
        }
        FavouriteFoods favouriteFoods = new FavouriteFoods();

        User user = userRepository.findByUserName(session.getAttribute("id").toString());
        Foods food = foodsRepository.findById(foodDTO.getFoodId()).get();

        favouriteFoods.setAmount(foodDTO.getAmount());
        favouriteFoods.setFoods(food);
        favouriteFoods.setUser(user);

        favouriteFoods = favouriteFoodsRepository.save(favouriteFoods);

        foodDTO.setAmount(favouriteFoods.getAmount());
        foodDTO.setUserId(user.getUserId());

        return foodDTO;
    }
}
