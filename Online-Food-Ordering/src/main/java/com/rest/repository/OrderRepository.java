package com.rest.repository;


import com.rest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByCustomerId(long userId);
    public List<Order> findByRestaurantId(long restaurantId);

}
