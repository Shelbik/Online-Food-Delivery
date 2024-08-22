package com.rest.service;

import com.rest.model.Cart;
import com.rest.model.CartItem;
import com.rest.request.AddCartItemRequest;

public interface CartService {

    CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;

    CartItem updateCartItemQuantity(long cartItemId, int quantity) throws Exception;

    Cart removeItemFromCart(long cartItemId, String jwt) throws Exception;

    Long calculateCartTotal(Cart cart) throws Exception;

    Cart findCartById(long id) throws Exception;

    Cart findCartByUserId(Long userId) throws Exception;


    Cart clearCart(Long userId) throws Exception;

}
