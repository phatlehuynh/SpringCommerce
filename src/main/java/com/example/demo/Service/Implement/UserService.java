package com.example.demo.Service.Implement;

import com.example.demo.Model.Cart;
import com.example.demo.Model.CartProduct;
import com.example.demo.Model.Product;
import com.example.demo.Model.User;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.InterfaceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService extends BaseService<User, UserRepository> implements InterfaceUserService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartService cartService;
    @Override
    public boolean addProduct(UUID userId, UUID productId, int quantity) throws NoSuchElementException {
        Optional<User> userOptional = repository.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            boolean result = cartService.addProductToCart(user.getCart().getId(), productId, quantity);
            if(!result){
                return false;
            }
            repository.save(user);
            return true;
        } else {
            throw new NoSuchElementException("userId: " + userId + "is not exists");
        }
    }

    public boolean removeProduct(UUID userId, UUID productId) throws NoSuchElementException {
        Optional<User> userOptional = repository.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            cartService.removeProductFromCart(user.getCart().getId(), productId);
            repository.save(user);
            return true;
        } else {
            throw new NoSuchElementException("userId: " + userId + "is not exists");
        }
    }

    @Override
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
