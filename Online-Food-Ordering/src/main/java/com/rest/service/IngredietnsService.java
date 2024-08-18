package com.rest.service;

import com.rest.model.IngredientCategory;
import com.rest.model.IngredientsItem;

import java.util.List;

public interface IngredietnsService {
    public IngredientCategory createIngredietnCategory(String name, Long restaurantId) throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredietnCategoryByRestaurantID(Long id) throws Exception;

    public IngredientsItem createIngredietnItem(Long restaurantId, String name, long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) throws Exception;

    public IngredientsItem updateStock(Long id) throws Exception;

}
