package com.example.demo.Service;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
import com.example.demo.Utilities.OrderCreationRequest;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

public interface InterfaceUserService {
    public User addProduct(UUID userId, Map<UUID, Integer> productIntegerMap);
}
