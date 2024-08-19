package com.rest.service.imp;

import com.rest.model.Category;
import com.rest.model.Food;
import com.rest.model.Restaurant;
import com.rest.repository.FoodRepository;
import com.rest.request.CreateFoodRequest;
import com.rest.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class FoodServiceImp implements FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setName(req.getName());
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setPrice(req.getPrice());
        food.setIngredientsItems(req.getIngredientsItemsList());
        food.setSeasonal(req.isSeasonal());
        food.setAvailable(req.isAvailable());
        food.setVegetarian(req.isVegetarian());
        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);

    }

    @Override
    public List<Food> getRestaurantsFood(long restaurantId, boolean isVegitarian, boolean isNonVeg, boolean isSeasonal, String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);
        if (isVegitarian) {
            foods = filterByVegeterian(foods, isVegitarian);
        }
        if (isNonVeg) {
            foods = filterByNonVeg(foods);
        }
        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }

        if (foodCategory != null && !foodCategory.equals("")) {
            foods = filterByCategory(foods, foodCategory);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food -> {
            if (food.getFoodCategory() != null) {
                return food.getFoodCategory().getName().equals(foodCategory);
            } else {
                return false;
            }
        }).collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream().filter(food -> food.isSeasonal() == isSeasonal).collect(Collectors.toList());

    }

    private List<Food> filterByNonVeg(List<Food> foods) {
        return foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());

    }

    private List<Food> filterByVegeterian(List<Food> foods, boolean isVegitarian) {
        return foods.stream().filter(food -> food.isVegetarian() == isVegitarian).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(long foodId) throws Exception {
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if (foodOptional.isEmpty()) {

            throw new Exception("Food no exists");
        } else {
            return foodOptional.get();
        }
    }

    @Override
    public Food updateFoodAvalibility(long foodId) throws Exception {
        Food food = findFoodById(foodId);

        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);

    }
}
