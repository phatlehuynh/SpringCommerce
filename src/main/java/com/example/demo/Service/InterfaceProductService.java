package com.example.demo.Service;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface InterfaceProductService extends InterfaceBaseService<Product, ProductRepository>{
    public Product update(UUID productId, Product newProduct);

    public String insertProduct(Product newProduct);
    public Page<Product> filter(UUID categoryId, String keyword, String brand, String color, int pageIndex, int pageSize);
        public Page<Product> getByCategoryId(UUID categoryId, int pageIndex, int pageSize);
    public Page<Product> getByUserId(UUID userId, int pageIndex, int pageSize);


    public Page<Product> search(String keyword, int pageIndex, int pageSize);

}
