package com.springsecurity.demo.controllers;

import com.springsecurity.demo.dto.FavouriteFoodDTO;
import com.springsecurity.demo.services.FavouriteFoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
