package com.rest.controller;

import com.rest.model.Cart;
import com.rest.model.CartItem;
import com.rest.request.AddCartItemRequest;
import com.rest.request.UpdateCartItemRequest;
import com.rest.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {


    @Autowired
    private CartService cartService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.addItemToCart(req, jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@PathVariable long id, @RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.removeItemFromCart(id, jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.clearCart(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.findCartByUserId(jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
