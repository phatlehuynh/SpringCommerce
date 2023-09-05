package com.example.demo.Service.Implement;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.Implement.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService<Product, ProductRepository> {
}
