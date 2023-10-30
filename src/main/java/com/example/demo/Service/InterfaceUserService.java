package com.example.demo.Service;

import com.example.demo.Model.Category;
import com.example.demo.Model.User;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.UserRepository;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface InterfaceUserService extends InterfaceBaseService<User, UserRepository>{
    public boolean addProduct(UUID userId, UUID productId, int quantity);
    public User updateInfo(UUID id, User newUser);
}
