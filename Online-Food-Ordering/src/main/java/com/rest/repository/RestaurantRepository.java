package com.rest.repository;

import com.rest.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select  r from Restaurant r where lower(r.name) like lower(concat('%',:query, '%')) " +
            "OR LOWER(r.cuisineType) like lower(concat('%',:query, '%') ) ")
    List<Restaurant> findBySearchQuery(String query);

    Restaurant findAllByOwnerId(Long userId);

}
