package com.rest.service;

import com.rest.model.Category;

import java.util.List;

public interface CategoryServis {
    public Category createCategory(String name, long userId) throws Exception;

    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws Exception;

    public Category findCategoryById(Long id) throws Exception;


}
