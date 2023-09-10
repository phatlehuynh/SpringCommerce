package com.example.demo.Service.Implement;

import com.example.demo.Model.Category;
import com.example.demo.Model.Product;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.Implement.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductService extends BaseService<Product, ProductRepository> {
    @Autowired
    CategoryRepository categoryRepository;
    public Page<Product> getByCategory(UUID categoryId, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return repository.findByCategoriesId(categoryId, pageable);
    }

    public Product addCategoryForProduct(UUID productId, UUID categoryId) {
        Product product = repository.findById(productId).get();
        Category category = categoryRepository.findById(categoryId).get();
        List<Category> categories = product.getCategories();
        categories.add(category);
        product.setCategories(categories);
        return repository.save(product);
    }
}
