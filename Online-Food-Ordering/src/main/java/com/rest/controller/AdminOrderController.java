package com.rest.controller;

import com.rest.model.Order;
import com.rest.model.User;
import com.rest.service.OrderService;
import com.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable long id, @RequestParam(required = false) String orderStaus, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getRestaurantOrder(id, orderStaus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @PutMapping("/order/{orderId}/{orderStatus")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable long id, @PathVariable String orderStaus, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrder(id, orderStaus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
