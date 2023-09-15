package com.example.demo.Service;

import com.example.demo.Model.Product;
import com.example.demo.Model.ProductCreationRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public interface InterfaceProductService {
    public Product updateInfo(UUID productId, ProductCreationRequest productCreationRequest);

    public Product insert(ProductCreationRequest productCreationRequest);
}
