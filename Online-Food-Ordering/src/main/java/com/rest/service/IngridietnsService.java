package com.rest.service;

import com.rest.model.IngredientsCategory;
import com.rest.model.IngredientsItem;

import java.util.List;

public interface IngridietnsService {
    public IngredientsCategory createIngridietnCategory(String name, Long restaurantId) throws Exception;

    public IngredientsCategory findIngridientCategory(Long id) throws Exception;

    public List<IngredientsCategory> findIngridietnCategoryByRestaurantID(Long id) throws Exception;

    public IngredientsItem createIngridietnItem(Long restaurantId,String name,long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantsIngridients(Long restaurantId) throws Exception;

    public IngredientsItem updateStock(Long id) throws Exception;

}
