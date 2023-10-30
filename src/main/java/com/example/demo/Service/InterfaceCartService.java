package com.example.demo.Service;

import com.example.demo.Model.Cart;
import com.example.demo.Repository.CartRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface InterfaceCartService extends InterfaceBaseService<Cart, CartRepository> {
    public boolean addProductToCart(UUID cartId, UUID productId, int quantity);
        public void removeProductFromCart(UUID cartId, UUID productId);
}
