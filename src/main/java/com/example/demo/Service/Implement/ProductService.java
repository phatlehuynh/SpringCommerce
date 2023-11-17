package com.example.demo.Service.Implement;

import com.example.demo.Model.CartProduct;
import com.example.demo.Model.Category;
import com.example.demo.Model.Product;
import com.example.demo.Repository.CartProductRepository;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.InterfaceProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService extends BaseService<Product, ProductRepository> implements InterfaceProductService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CartProductRepository cartProductRepository;
    public Page<Product> getByCategoryId(UUID categoryId, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return repository.findByCategoryId(categoryId, pageable);
    }

    public Page<Product> search(String keyword, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return repository.search(keyword, pageable);
    }

    @Override
    public Product update(UUID productId, Product newProduct) throws NoSuchElementException{
        Optional<Product> productOptional = repository.findById(productId);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            newProduct.setId(productId);
            Category category = newProduct.getCategory();
            if(category == null){
                Category oldCategory = product.getCategory();
                if(oldCategory != null) {
                    newProduct.setCategory(product.getCategory());
                }
            }
            return repository.save(newProduct);
        } else {
            throw new NoSuchElementException("Can't find product with id: " + productId.toString());
        }
    }

    @Override
    public Product insert(Product newProduct) throws NoSuchElementException {
            Category category = newProduct.getCategory();
            if(category != null){
                Optional<Category> categoryOptional = categoryRepository.findById(newProduct.getCategoryId());
                if(categoryOptional.isEmpty()){
                    throw new NoSuchElementException("categoryId: " + newProduct.getCategoryId() + " is not exists");
                }
            }
            return repository.save(newProduct);
    }

    @Override
    public void deleteById(UUID id) throws NoSuchElementException {
        Optional <Product> productOptional = repository.findById(id);
        if(productOptional.isPresent()) {
            Product product = productOptional.get();
            Optional<CartProduct> cartProductOptional = cartProductRepository.findByProductId(id);
            if(cartProductOptional.isPresent()) {
                product.setDeleted(true);
                repository.save(product);
            } else {
                repository.deleteById(id);
            }
        } else {
            throw new NoSuchElementException("Can't found productId: " + id + " to delete");
        }
    }

    @Override
    public Page<Product> getPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return repository.findAll(pageable);
    }
}
