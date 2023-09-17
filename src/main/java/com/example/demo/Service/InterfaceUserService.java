package com.example.demo.Service;

import com.example.demo.Model.Order;

import java.util.Set;
import java.util.UUID;

public interface InterfaceUserService {
    public Set<Order> getOrders(UUID idUser);
}
