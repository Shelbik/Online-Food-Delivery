package com.rest.service.imp;

import com.rest.model.IngredientCategory;
import com.rest.model.IngredientsItem;
import com.rest.model.Restaurant;
import com.rest.repository.IngredienItemRepository;
import com.rest.repository.IngredientCategoryRepository;
import com.rest.service.IngredietnsService;
import com.rest.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredietnsService {

    @Autowired
    private IngredienItemRepository ingredientItemRepository;
    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredietnCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> optionalIngredientCategory = ingredientCategoryRepository.findById(id);
        if (optionalIngredientCategory.isEmpty()) {
            throw new Exception("Ingridient Category not found");
        }
        return optionalIngredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredietnCategoryByRestaurantID(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);

    }

    @Override
    public IngredientsItem createIngredietnItem(Long restaurantId, String name, long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantUserId(restaurantId);
        IngredientCategory ingredientCategory = findIngredientCategoryById(categoryId);
        IngredientsItem ingredientsItem = new IngredientsItem();
        ingredientsItem.setName(name);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(ingredientCategory);
        IngredientsItem item = ingredientItemRepository.save(ingredientsItem);
        ingredientCategory.getIngredientsItems().add(item);

        return item;
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);

    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem = ingredientItemRepository.findById(id);
        if (optionalIngredientsItem.isEmpty()) {
            throw new Exception("Ingredient not found");
        }
        IngredientsItem ingredientsItem = optionalIngredientsItem.get();
        ingredientsItem.setInStoke(!ingredientsItem.isInStoke());
        return ingredientItemRepository.save(ingredientsItem);
    }
}
