package com.rest.request;

import com.rest.model.Category;
import com.rest.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;
    private Category foodCategory;
    private List<String> images;
    private boolean available;
    private Long restaurantId;
    private boolean isVegetarian;
    private boolean isSeasonal;
    private List<IngredientsItem> ingredientsItemsList;
}
