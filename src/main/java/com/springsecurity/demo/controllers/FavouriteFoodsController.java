package com.springsecurity.demo.controllers;

import com.springsecurity.demo.dto.FavouriteDTO;
import com.springsecurity.demo.dto.FavouriteFoodDTO;
import com.springsecurity.demo.dto.FoodDTO;
import com.springsecurity.demo.services.FavouriteFoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class FavouriteFoodsController {

    private final FavouriteFoodsService favouriteFoodsService;

    @Autowired
    public FavouriteFoodsController(FavouriteFoodsService favouriteFoodsService) {
        this.favouriteFoodsService = favouriteFoodsService;
    }

    @PostMapping(path = "/add-favourite")
    public FavouriteFoodDTO addToFavourite(@RequestBody FavouriteFoodDTO favouriteFoodDTO) {
        return favouriteFoodsService.addToFavourite(favouriteFoodDTO);
    }

    @PostMapping(path = "/delete-favourite")
    public FavouriteDTO deleteFromFavourite(@RequestBody FavouriteDTO favouriteDTO) {
        return favouriteFoodsService.deleteFromFavourite(favouriteDTO);
    }

    @GetMapping(path = "/get-favourite")
    public List<FavouriteDTO> getFavouriteFoods() {
        return favouriteFoodsService.getFavourite();
    }

    @GetMapping(path = "/get-foods")
    public List<FoodDTO> getFoods() {
        return favouriteFoodsService.getFoods();
    }
}
