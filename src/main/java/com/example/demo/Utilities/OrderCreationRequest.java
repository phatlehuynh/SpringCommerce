package com.example.demo.Utilities;

import com.example.demo.Model.Order;
import com.example.demo.Model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCreationRequest {
    private Order order;
    private List<UUID> productIdList;
}
