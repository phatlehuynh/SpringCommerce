package com.example.demo.Service.Implement;

import com.example.demo.Model.Category;
import com.example.demo.Model.Product;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService extends BaseService<Product, ProductRepository> {
    @Autowired
    CategoryRepository categoryRepository;
    public Page<Product> getByCategory(UUID categoryId, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return repository.findByCategoriesId(categoryId, pageable);
    }

    public Product addCategoryForProduct(UUID productId, UUID categoryId) throws NoSuchElementException{
        Optional<Product> product = repository.findById(productId);
        if(product.isPresent()) {
            Optional<Category> category = categoryRepository.findById(categoryId);
            if (category.isPresent()) {
                List<Category> categories = product.get().getCategories();
                categories.add(category.get());
                product.get().setCategories(categories);
                return repository.save(product.get());
            }
            throw new NoSuchElementException("cannot found category id: " + categoryId.toString());
        }
        throw new NoSuchElementException("cannot found product id: " + productId.toString());
    }

    public Page<Product> search(String keyword, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return repository.search(keyword, pageable);
    }
}
