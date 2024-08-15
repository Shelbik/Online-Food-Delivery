package com.rest.service;

import com.rest.dto.RestaurantDto;
import com.rest.model.Restaurant;
import com.rest.model.User;
import com.rest.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String keyWord);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;


}
