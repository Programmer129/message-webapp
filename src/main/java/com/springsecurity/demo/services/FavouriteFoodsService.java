package com.springsecurity.demo.services;

import com.springsecurity.demo.dto.FavouriteDTO;
import com.springsecurity.demo.dto.FavouriteFoodDTO;
import com.springsecurity.demo.dto.FoodDTO;
import com.springsecurity.demo.entities.FavouriteFoods;
import com.springsecurity.demo.entities.Food;
import com.springsecurity.demo.entities.User;
import com.springsecurity.demo.exceptions.NotEnoughMoneyException;
import com.springsecurity.demo.exceptions.UnauthorisedException;
import com.springsecurity.demo.repositories.FavouriteFoodsRepository;
import com.springsecurity.demo.repositories.FoodsRepository;
import com.springsecurity.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Component
@Transactional
public class FavouriteFoodsService {

    private final FavouriteFoodsRepository favouriteFoodsRepository;
    private final UserRepository userRepository;
    private final FoodsRepository foodsRepository;

    @Autowired
    public FavouriteFoodsService(FavouriteFoodsRepository favouriteFoodsRepository,
                                 UserRepository userRepository, FoodsRepository foodsRepository) {
        this.favouriteFoodsRepository = favouriteFoodsRepository;
        this.userRepository = userRepository;
        this.foodsRepository = foodsRepository;
    }

    @Transactional
    public FavouriteFoodDTO addToFavourite(FavouriteFoodDTO foodDTO) {
        FavouriteFoods favouriteFoods = new FavouriteFoods();


        User user = userRepository.findByUserName(getCurrentUserName());
        Food food = foodsRepository.findById(foodDTO.getFoodId()).get();

        favouriteFoods.setAmount(foodDTO.getAmount());
        favouriteFoods.setFood(food);
        favouriteFoods.setUser(user);

        Set<FavouriteFoods> foods = user.getFavouriteFoods();

        boolean was = false;
        int amount = 0;
        for (FavouriteFoods food1 : foods) {
            if(food1.getFood().getId().equals(foodDTO.getFoodId())){
                was=true;
                amount = food1.getAmount();
                break;
            }
        }

        if(was) {
            favouriteFoodsRepository.updateAmount(foodDTO.getAmount() + amount, user);
        }
        else {
            favouriteFoods = favouriteFoodsRepository.save(favouriteFoods);
        }

        foodDTO.setAmount(favouriteFoods.getAmount() + amount);
        foodDTO.setUserId(user.getUserId());
        foodDTO.setId(favouriteFoods.getId());

        return foodDTO;
    }

    @Transactional
    public FavouriteDTO deleteFromFavourite(FavouriteDTO foodDTO) {
        User user = userRepository.findByUserName(getCurrentUserName());
        Food food = foodsRepository.findByName(foodDTO.getName());

        List<FavouriteFoods> collect = user.getFavouriteFoods().stream()
                .filter(a -> a.getFood().getName().equals(foodDTO.getName()))
                .collect(Collectors.toList());

        if(user.getUserCard().getBalance().doubleValue() - foodDTO.getTotalPrice().doubleValue() < 0) {
            throw new NotEnoughMoneyException();
        }

        user.getUserCard().setBalance(BigDecimal.valueOf(user.getUserCard().getBalance().doubleValue() - foodDTO.getTotalPrice().doubleValue()));

        user.getFavouriteFoods().remove(collect.get(0));
        food.getFavouriteFoods().remove(collect.get(0));
        favouriteFoodsRepository.deleteById(collect.get(0).getId());

        return foodDTO;
    }

    @Transactional
    public List<FoodDTO> getFoods() {
        return foodsRepository.findAll().stream().map(this::mapToFoodDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<FavouriteDTO> getFavourite() {
        User user = userRepository.findByUserName(getCurrentUserName());
        return user
                .getFavouriteFoods().stream()
                .map(item -> mapToFavouriteDTO(item.getFood(), item.getAmount()))
                .collect(Collectors.toList());
    }

    public List<FoodDTO> searchFoods(String name) {
        return foodsRepository.findByNameStartingWith(name).stream().map(this::mapToFoodDTO).collect(Collectors.toList());
    }

    private String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private FoodDTO mapToFoodDTO(Food food) {
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

    private FavouriteDTO mapToFavouriteDTO(Food food, Integer amount) {
        FavouriteDTO favouriteDTO = new FavouriteDTO();

        favouriteDTO.setName(food.getName());
        favouriteDTO.setCategory(food.getCategory());
        favouriteDTO.setTotalPrice(food.getPrice().multiply(new BigDecimal(amount)));

        return favouriteDTO;
    }
}
