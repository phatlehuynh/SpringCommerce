package com.example.demo.Service;

import com.example.demo.Model.Order;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface InterfaceOrderService {
    public Order insert(Order newOrder);
    public Order updateStatus(UUID id, byte newStatus);
    public Page<Order> getByUserId(UUID userId, int pageIndex, int pageSize);
    }
