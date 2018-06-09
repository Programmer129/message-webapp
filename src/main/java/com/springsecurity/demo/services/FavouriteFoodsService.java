package com.springsecurity.demo.services;

import com.springsecurity.demo.dto.FavouriteFoodDTO;
import com.springsecurity.demo.dto.FoodDTO;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        foodDTO.setId(favouriteFoods.getId());

        return foodDTO;
    }

    @Transactional
    public FavouriteFoodDTO deleteFromFavourite(FavouriteFoodDTO foodDTO) {
        if(Objects.isNull(session.getAttribute("id"))) {
            throw new UnauthorisedException();
        }
        User user = userRepository.findByUserName(session.getAttribute("id").toString());

        favouriteFoodsRepository.deleteById(foodDTO.getId());

        foodDTO.setUserId(user.getUserId());

        return foodDTO;
    }

    @Transactional
    public List<FoodDTO> getFoods() {
        return foodsRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private FoodDTO mapToDTO(Foods food) {
        FoodDTO foodDTO = new FoodDTO();

        foodDTO.setId(food.getId());
        foodDTO.setCategory(food.getCategory());
        foodDTO.setIsImported(food.getIsImported());
        foodDTO.setIsStock(food.getIsStock());
        foodDTO.setName(food.getName());
        foodDTO.setMaxStock(food.getMaxStock());
        foodDTO.setPrice(food.getPrice());

        return foodDTO;
    }
}
