package com.example.demo.Service;

import com.example.demo.Model.Order;
import com.example.demo.Model.OrderStatus;
import org.springframework.data.domain.Page;

import java.util.NoSuchElementException;
import java.util.UUID;

public interface InterfaceOrderService {
    public boolean create(Order newOrder);
    public boolean updateStatus(UUID id, OrderStatus newStatus);
    public Page<Order> getByUserId(UUID userId, int pageIndex, int pageSize);
    public Page<Order> getPageOrder(OrderStatus orderStatus, int pageIndex, int pageSize);
}
