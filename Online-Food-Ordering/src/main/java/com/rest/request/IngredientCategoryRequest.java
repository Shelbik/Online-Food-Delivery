package com.rest.request;

import lombok.Data;

@Data
public class IngredientCategoryRequest {
    private String name;
    private long restaurantId;
}
