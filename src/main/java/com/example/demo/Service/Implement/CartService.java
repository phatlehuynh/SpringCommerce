package com.example.demo.Service.Implement;

import com.example.demo.Model.Cart;
import com.example.demo.Model.CartProduct;
import com.example.demo.Model.Product;
import com.example.demo.Repository.CartProductRepository;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.InterfaceCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService extends BaseService<Cart, CartRepository> implements InterfaceCartService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartProductRepository cartProductRepository;

    @Override
    public boolean addProductToCart(UUID cartId, UUID productId, int quantity) throws NoSuchElementException{
        Optional<Cart> cartOptional = repository.findById(cartId);
        if(cartOptional.isPresent()){
            Cart cart = cartOptional.get();
            Optional<Product> productOptional = productRepository.findById(productId);
            if(productOptional.isPresent()) {
                Product product = productOptional.get();
                CartProduct cartProduct = cart.changeQuantityCartProduct(productId, quantity);
                if(cartProduct == null) {
                    if(quantity < 1) {
                        return false;
                    }
                    CartProduct newCartProduct = new CartProduct(cartOptional.get(), product, quantity);
                    cartProductRepository.save(newCartProduct);
                }
                else {
                    if(cartProduct.getQuantity() < 1) {
                        cartProductRepository.deleteById(cartProduct.getId());
                    }
                    repository.save(cart);
                }
                return true;
            } else {
                throw new NoSuchElementException("ProductId: " + productId + "is not exist");
            }
        } else {
            throw new NoSuchElementException("CartId: " + cartId + "is not exist");
        }
    }

    @Override
    public void removeProductFromCart(UUID cartId, UUID productId) throws NoSuchElementException {
        Optional<Cart> cartOptional = repository.findById(cartId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            CartProduct cartProduct = cart.findCardProductByProductId(productId);
            if (cartProduct != null) {
                cart.removeCartProduct(cartProduct);
//                cartProductRepository.deleteById(cartProduct.getId());
                repository.save(cart);
            } else {
                throw new NoSuchElementException("ProductId: " + productId + " is not exist");
            }
        } else {
            throw new NoSuchElementException("CartId: " + cartId + " is not exist");
        }
    }
}