package com.rest.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;
@Data
@Embeddable
public class RestaurantDto {

    private String Title;

    @Column(length = 1000)
    private List<String> images;

    private String description;
    private Long id;


}
