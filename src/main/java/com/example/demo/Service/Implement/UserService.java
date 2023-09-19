package com.example.demo.Service.Implement;

import com.example.demo.Model.*;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.InterfaceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService extends BaseService<User, UserRepository> implements InterfaceUserService{
    @Autowired
    ProductRepository productRepository;
    @Override
    public User addProduct(UUID userId, Map<UUID, Integer> productIntegerMap) throws NoSuchElementException {
        Optional<User> userOptional = repository.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            for(Map.Entry<UUID, Integer> entry : productIntegerMap.entrySet()) {
                UUID productId = entry.getKey();
                Integer quantity = entry.getValue();
                Optional<Product> productOptional = productRepository.findById(productId);
                if(productOptional.isPresent()) {
                    Cart userCart = user.getCart();
                    Product product = productOptional.get();
                    CartProduct cartProduct = new CartProduct(userCart, product, quantity);
                    user.getCart().addCartProduct(cartProduct);
                } else {
                    throw new NoSuchElementException("product id: " + productId + " id not exists");
                }
            }
            return repository.save(user);
        } else {
            throw new NoSuchElementException("userId: " + userId + "is not exists");
        }
    }

    public User updateInfo(UUID id, User newUser) {
        Optional<User> userOptional = repository.findById(id);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            newUser.setId(user.getId());
            newUser.setCart(user.getCart());
            return repository.save(newUser);
        }
        throw new NoSuchElementException("userId: " + id + "is not exist");
    }

}
