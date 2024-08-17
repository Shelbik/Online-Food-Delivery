package com.rest.controller;

import com.rest.model.Food;
import com.rest.model.Restaurant;
import com.rest.model.User;
import com.rest.request.CreateFoodRequest;
import com.rest.response.MessageResponse;
import com.rest.service.FoodService;
import com.rest.service.RestaurantService;
import com.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getFoodCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Food deleted succefully");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable long id,
                                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateFoodAvalibility(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }

}
