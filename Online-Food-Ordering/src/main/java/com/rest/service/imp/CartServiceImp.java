package com.rest.service.imp;

import com.rest.model.Cart;
import com.rest.model.CartItem;
import com.rest.model.Food;
import com.rest.model.User;
import com.rest.repository.CartItemRepository;
import com.rest.repository.CartRepository;
import com.rest.request.AddCartItemRequest;
import com.rest.service.CartService;
import com.rest.service.FoodService;
import com.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImp implements CartService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.findFoodById(req.getFoodId());
        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setCart(cart);
        newCartItem.setIngridients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity() * food.getPrice());
        CartItem savedCartItem = cartItemRepository.save(newCartItem);

        cart.getCartItems().add(savedCartItem);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(long cartItemId, int quantity) throws Exception {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()) {
            throw new Exception("Cart item not found");
        }
        CartItem item = cartItemOptional.get();
        item.setQuantity(quantity);

        item.setTotalPrice(item.getFood().getPrice() * quantity);
        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(long cartItemId, String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isEmpty()) {
            throw new Exception("Cart item not found");
        }
        CartItem item = cartItem.get();
        cart.getCartItems().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) {
        long total = 0L;
        for (CartItem cartItem : cart.getCartItems()) {
            total += cartItem.getFood().getPrice() * cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(long id) throws Exception {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isEmpty()) {
            throw new Exception("Cart not found with id " + id);
        }
        return cart.get();
    }

    @Override
    public Cart findCartByUserId(String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        return cartRepository.findByCustomerId(user.getId());
    }

    @Override
    public Cart clearCart(String jwt) throws Exception {

        Cart cart = findCartByUserId(jwt);
        cart.getCartItems().clear();
        return cartRepository.save(cart);
    }
}
