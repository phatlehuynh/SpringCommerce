package com.example.demo.Service;

import com.example.demo.Model.Order;
import com.example.demo.Utilities.OrderCreationRequest;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface InterfaceOrderService {
    public Order insert(Order newOrder);
    public Order updateStatus(UUID id, byte newStatus);
}
