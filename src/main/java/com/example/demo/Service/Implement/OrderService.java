package com.example.demo.Service.Implement;

import com.example.demo.Model.Category;
import com.example.demo.Model.Order;
import com.example.demo.Model.Product;
import com.example.demo.Model.User;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.InterfaceOrderService;
import com.example.demo.Utilities.OrderCreationRequest;
import com.example.demo.Utilities.ProductCreationRequest;
import com.example.demo.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService extends BaseService<Order, OrderRepository> implements InterfaceOrderService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Order insert(OrderCreationRequest orderCreationRequest) throws NoSuchElementException {
        Order newOrder = orderCreationRequest.getOrder();
        Optional<User> userOptional= userRepository.findById(newOrder.getUser().getId());
        if(userOptional.isPresent()) {
            newOrder.setUser(userOptional.get());
        } else {
            throw new NoSuchElementException("User have id: " + newOrder.getUser().getId() + " is not exists");
        }
        List<UUID> productIdList = orderCreationRequest.getProductIdList();
        if(productIdList != null){
            newOrder.setProducts(new HashSet<>());
            for(UUID productId : productIdList) {
                Optional<Product> productOptional = productRepository.findById(productId);
                if(productOptional.isPresent()){
                    newOrder.addProduct(productOptional.get());
                } else {
                    throw new NoSuchElementException("productId: " + productId + " is not exists");
                }
            }
        }
        return repository.save(newOrder);
    }
}
