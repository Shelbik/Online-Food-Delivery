package com.rest.controller;

import com.rest.model.Food;
import com.rest.model.User;
import com.rest.service.FoodService;
import com.rest.service.RestaurantService;
import com.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.searchFood(keyword);


        return new ResponseEntity<>(foods, HttpStatus.CREATED);

    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@PathVariable Long restaurantId,
                                                        @RequestParam boolean vegeterian,
                                                        @RequestParam boolean seasonal,
                                                        @RequestParam boolean nonveg,
                                                        @RequestParam(required = false) String food_category,

                                                        @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegeterian, nonveg, seasonal, food_category);


        return new ResponseEntity<>(foods, HttpStatus.OK);

    }
}
