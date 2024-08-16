package com.rest.controller;

import com.rest.model.Restaurant;
import com.rest.model.User;
import com.rest.request.CreateRestaurantRequest;
import com.rest.response.MessageResponse;
import com.rest.service.RestaurantService;
import com.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant
            (@RequestBody CreateRestaurantRequest req, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.createRestaurant(req, user);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant
            (@RequestBody CreateRestaurantRequest req,
             @PathVariable Long id) throws Exception {

        Restaurant restaurant = restaurantService.updateRestaurant(id, req);

        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant
            (@RequestHeader("Authorization") String jwt,
             @PathVariable Long id) throws Exception {


        restaurantService.deleteRestaurant(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Restaurant is deleted successfully");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus
            (@RequestHeader("Authorization") String jwt,
             @PathVariable Long id) throws Exception {

        Restaurant restaurant = restaurantService.findRestaurantById(id);
        restaurantService.updateRestaurantStatus(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Restaurant is updated successfully");

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId
            (@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.getRestaurantUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}

