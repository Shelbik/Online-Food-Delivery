package com.rest.service;

import com.rest.model.Category;
import com.rest.model.Food;
import com.rest.model.Restaurant;
import com.rest.request.CreateFoodRequest;

import java.util.List;

public interface FoodInterface {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(long restaurantId, boolean isVegitarian,
                                         boolean isNonVeg, boolean isSeasonal, String foodCategory);

    public List<Food> searchFood(String keyword);

    public Food findFoodById(long foodId) throws Exception;

    public Food updateFoodAvalibility(long foodId) throws Exception;


}
