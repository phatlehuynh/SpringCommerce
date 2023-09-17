package com.example.demo.Service;

import com.example.demo.Model.Order;
import com.example.demo.Utilities.OrderCreationRequest;

import java.util.NoSuchElementException;

public interface InterfaceOrderService {
    public Order insert(OrderCreationRequest orderCreationRequest);
}
