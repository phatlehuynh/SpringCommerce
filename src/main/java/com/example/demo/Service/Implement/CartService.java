package com.example.demo.Service.Implement;

import com.example.demo.Model.*;
import com.example.demo.Repository.*;
import com.example.demo.Service.InterfaceCartService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService extends BaseService<Cart, CartRepository> implements InterfaceCartService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartProductRepository cartProductRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public Cart insert(Map<UUID, Integer> productIdAndQuantityList) {
        Cart newCart = new Cart();
        for(Map.Entry<UUID, Integer> entry : productIdAndQuantityList.entrySet()) {
            Optional<Product> productOptional = productRepository.findById(entry.getKey());
            if(productOptional.isPresent()){
                CartProduct cartProduct = new CartProduct(newCart, productOptional.get(), entry.getValue());
                newCart.addCartProduct(cartProduct);
            }
            else {
                throw new NoSuchElementException("Cannot found product have id: " + entry.getKey());
            }
        }
        return repository.save(newCart);
    }

    public Cart insert() {
        Cart newCart = new Cart();
        return repository.save(newCart);
    }
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
                    cart.addCartProduct(newCartProduct);
                }
                else {
                    if(cartProduct.getQuantity() < 1) {
                        cartProductRepository.deleteById(cartProduct.getId());
                    }
                }
                repository.save(cart);
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

    @Override
    public void deleteById(UUID id) throws NoSuchElementException, NotImplementedException {
        Optional<Cart> cartOptional = repository.findById(id);
        if (cartOptional.isPresent()) {
            Optional<Order> orderOptional = orderRepository.findByCartId(id);
            if(orderOptional.isPresent()) {
                throw new NotImplementedException("Cannot delete existing cart in order");
            }
            Optional<User> userOptional = userRepository.findByCartId(id);
            if(userOptional.isPresent()) {
                throw new NotImplementedException("Cannot delete cart of user");
            }
            repository.deleteById(id);
        } else {
            throw new NoSuchElementException("Can't found productId: " + id + " to delete");
        }
    }
}