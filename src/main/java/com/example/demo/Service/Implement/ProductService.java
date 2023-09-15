package com.example.demo.Service.Implement;

import com.example.demo.Model.Category;
import com.example.demo.Model.Product;
import com.example.demo.Model.ProductCreationRequest;
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
    public Page<Product> getByCategory(UUID categoryId, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return repository.findByCategoriesId(categoryId, pageable);
    }

    public Product addCategoryForProduct(UUID productId, UUID categoryId) throws NoSuchElementException{
        Optional<Product> product = repository.findById(productId);
        if(product.isPresent()) {
            Optional<Category> category = categoryRepository.findById(categoryId);
            if (category.isPresent()) {
                product.get().addCategory(category.get());
                return repository.save(product.get());
            }
            throw new NoSuchElementException("cannot found category id: " + categoryId.toString());
        } else {
            throw new NoSuchElementException("cannot found product id: " + productId.toString());
        }
    }

    public Page<Product> search(String keyword, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return repository.search(keyword, pageable);
    }

    @Override
    public Product updateInfo(UUID productId, ProductCreationRequest productCreationRequest) throws NoSuchElementException{
        Optional<Product> product = repository.findById(productId);
        if(product.isPresent()){
            Product newProduct = productCreationRequest.getProduct();
            newProduct.setId(productId);
            List<UUID> categories = productCreationRequest.getCategoryIdList();
            if(categories != null){
                newProduct.setCategories(new HashSet<>());
                for(UUID categoryId : categories) {
                    Optional<Category> category = categoryRepository.findById(categoryId);
                    if(category.isPresent()){
                        newProduct.addCategory(category.get());
                    } else {
                        throw new NoSuchElementException("categoryId: " + categoryId + " is not exists");
                    }
                }
            } else {
                newProduct.setCategories(product.get().getCategories());
            }
            return repository.save(newProduct);
        } else {
            throw new NoSuchElementException("Can't find product with id: " + productId.toString());
        }
    }

    @Override
    public Product insert(ProductCreationRequest productCreationRequest) throws NoSuchElementException {
            Product newProduct = productCreationRequest.getProduct();
            List<UUID> categories = productCreationRequest.getCategoryIdList();
            if(categories != null){
                newProduct.setCategories(new HashSet<>());
                for(UUID categoryId : categories) {
                    Optional<Category> category = categoryRepository.findById(categoryId);
                    if(category.isPresent()){
                        newProduct.addCategory(category.get());
                    } else {
                        throw new NoSuchElementException("categoryId: " + categoryId + " is not exists");
                    }
                }
            }
            return repository.save(newProduct);
    }

    @Override
    public void deleteById(UUID id) throws NoSuchElementException {
        Optional <Product> product = repository.findById(id);
        if(product.isPresent()) {
            product.get().setCategories(new HashSet<>());
            repository.deleteById(id);
        } else {
            throw new NoSuchElementException("Can't found productId: " + id + " to delete");
        }
    }
}
