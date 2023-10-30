package com.example.demo.Service;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface InterfaceProductService extends InterfaceBaseService<Product, ProductRepository>{
    public Product update(UUID productId, Product newProduct);

    public Product insert(Product newProduct);

    public Page<Product> getByCategory(UUID categoryId, int pageIndex, int pageSize);

    public Page<Product> search(String keyword, int pageIndex, int pageSize);

    public Product addCategoryForProduct(UUID productId, UUID categoryId);
}
