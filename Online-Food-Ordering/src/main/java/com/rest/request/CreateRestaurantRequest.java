package com.rest.request;

import com.rest.model.Address;
import com.rest.model.ContactInfromation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInfromation contactInfromation;
    private String openingHours;
    private List<String> images;

}
