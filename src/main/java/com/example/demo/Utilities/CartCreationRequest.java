package com.example.demo.Utilities;

import com.example.demo.Model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartCreationRequest {
    private Cart cart;
    private Map<UUID, Integer> ProductIdQuantityList;
}
