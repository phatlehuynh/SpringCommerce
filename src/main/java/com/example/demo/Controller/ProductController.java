package com.example.demo.Controller;

import com.example.demo.Model.Category;
import com.example.demo.Model.Product;
import com.example.demo.Model.ProductCreationRequest;
import com.example.demo.Response;
import com.example.demo.Service.Implement.ProductService;
import com.example.demo.utilities.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<?> getAll() {
        return Response.createResponse(HttpStatus.OK, "get all product successfully", productService.getAll());
    }

    @GetMapping("/products/page")
    public ResponseEntity<?> getPage(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Product> products;
        if(categoryId != null) {
            products = productService.getByCategory(categoryId, pageIndex, pageSize);
        }
        else {
            products = productService.getPage(pageIndex, pageSize);
        }
        PaginatedResponse<Product> paginatedResponse = new PaginatedResponse<>(products.getContent(), products.getTotalElements(), products.getTotalPages());
        return Response.createResponse(HttpStatus.OK, "get products successfully", paginatedResponse);
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            Product product = productService.findById(id);
            return Response.createResponse(HttpStatus.OK, "get product by id successfully", product);
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
        }
    }

    @PostMapping("/product/insert")
    public ResponseEntity<?> insert(@RequestBody ProductCreationRequest productCreationRequest) throws NoSuchElementException{
        Product product = productCreationRequest.getProduct();
        List<UUID> categoryIdList = productCreationRequest.getCategoryIdList();
        Category categoryTemp = null;
        Product productSaved =  productService.create(product);
        if(categoryIdList != null){
            for(UUID categoryId : categoryIdList){
                try {
                    productService.addCategoryForProduct(productSaved.getId(), categoryId);
                } catch (NoSuchElementException e){
                    return Response.createResponse(HttpStatus.OK, "add category for product failed", productSaved);
                }
            }
        }

        return Response.createResponse(HttpStatus.OK, "Insert product successfully", productService.findById(product.getId()));
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if(productService.deleteById(id)){
            return Response.createResponse(HttpStatus.OK, "deleted product have id: " + id.toString(), null);
        }
        return Response.createResponse(HttpStatus.OK, "cannot found product have id: " + id.toString() + " to delete", null);

    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Product newProduct) {
        try {
            return Response.createResponse(HttpStatus.OK, "update product successfully", productService.update(id, newProduct));
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);

        }
    }

    @GetMapping("/product/getbycategory/{categoryId}")
    public ResponseEntity<?> getByCategory(
            @PathVariable UUID categoryId,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Product> products = productService.getByCategory(categoryId, pageIndex, pageSize);
        PaginatedResponse<Product> paginatedResponse = new PaginatedResponse<>(products.getContent(), products.getTotalElements(), products.getTotalPages());
        return Response.createResponse(HttpStatus.OK, "get products by category successfully", paginatedResponse);
    }

    @PutMapping("/product/{productId}/category/{categoryId}")
    public ResponseEntity<?> addCategoryForProduct(@PathVariable UUID productId, @PathVariable UUID categoryId) {
        return Response.createResponse(HttpStatus.OK, "ok", productService.addCategoryForProduct(productId, categoryId));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        Product productA = new Product();
        productA.setTitle("product A");
        Category categoryA = new Category();
        categoryA.setTitle("category A");
        List<Category> categorySet = new ArrayList<>();
        categorySet.add(categoryA);
        Category categoryB = new Category();
        categoryA.setTitle("category B");
        categorySet.add(categoryB);

        productA.setCategories(categorySet);
        return Response.createResponse(HttpStatus.OK, "ok", productService.create(productA));
    }
}
