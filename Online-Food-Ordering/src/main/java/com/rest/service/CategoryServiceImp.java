package com.rest.service;

import com.rest.model.Category;
import com.rest.model.Restaurant;
import com.rest.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryServis {

    private CategoryRepository categoryRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public Category createCategory(String name, long userId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantUserId(userId);
        if (restaurant != null) {
            Category category = new Category();
            category.setName(name);
            category.setRestaurant(restaurant);
            return categoryRepository.save(category);
        } else {
            return null;
        }
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantUserId(restaurantId);
        return categoryRepository.findByRestaurantId(restaurant.getId());
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new Exception("Category not found");
        } else {
            return optionalCategory.get();
        }
    }
}
