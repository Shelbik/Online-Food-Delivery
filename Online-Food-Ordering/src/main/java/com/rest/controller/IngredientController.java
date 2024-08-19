package com.rest.controller;

import com.rest.model.IngredientCategory;
import com.rest.model.IngredientsItem;
import com.rest.request.IngredientCategoryRequest;
import com.rest.request.IngredientRequest;
import com.rest.service.IngredietnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
    @Autowired
    private IngredietnsService ingredietnsService;


    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req) throws Exception {
        IngredientCategory ingredientCategory = ingredietnsService.createIngredietnCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping("")
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientRequest req) throws Exception {
        IngredientsItem ingredientsItem = ingredietnsService.createIngredietnItem(req.getRestaurantId(), req.getName(), req.getCategoryId());
        return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateStoke(@PathVariable long id) throws Exception {
        IngredientsItem ingredientsItem = ingredietnsService.updateStock(id);
        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(@PathVariable long id) throws Exception {
        List<IngredientsItem> items = ingredietnsService.findRestaurantsIngredients(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<IngredientCategory> findRestaurantIngredientCategory(@PathVariable long id) throws Exception {
        IngredientCategory ingredientCategory = ingredietnsService.findIngredientCategoryById(id);
        return new ResponseEntity<>(ingredientCategory, HttpStatus.OK);
    }
}
