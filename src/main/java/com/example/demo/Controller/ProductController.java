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
        return Response.createResponse(HttpStatus.OK,
                "get all product successfully",
                productService.getAll());
    }

    @GetMapping("/products/page")
    public ResponseEntity<?> getPage(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Product> products;
        if(categoryId != null) {
            products = productService.getByCategory(categoryId, pageIndex, pageSize);
        } else if (keyword != null) {
            products = productService.search(keyword, pageIndex, pageSize);
        } else {
            products = productService.getPage(pageIndex, pageSize);
        }
        PaginatedResponse<Product> paginatedResponse = new PaginatedResponse<>(
                products.getContent(), products.getTotalElements(), products.getTotalPages()
        );
        return Response.createResponse(HttpStatus.OK, "get products successfully", paginatedResponse);
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) throws NoSuchElementException{
        try {
            return Response.createResponse(HttpStatus.OK,
                    "get product by id successfully",
                    productService.getById(id));
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
        }
    }

    @PostMapping("/product/insert")
    public ResponseEntity<?> insert(
            @RequestBody ProductCreationRequest productCreationRequest
    ) throws NoSuchElementException {
        try {
            return Response.createResponse(
                    HttpStatus.OK,
                    "insert product successfully",
                    productService.insert(productCreationRequest)
            );
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
        }
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws NoSuchElementException {
        try {
            productService.deleteById(id);
            return Response.createResponse(HttpStatus.OK,
                    "deleted product have id: " + id.toString(),
                    null);

        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.OK, e.getMessage(), null);
        }

    }


    // update both product and category information
    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Product newProduct) {
        try {
            return Response.createResponse(HttpStatus.OK, "update product successfully", productService.update(id, newProduct));
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);

        }
    }

    // update product information
    @PutMapping("/product/updateinfo/{id}")
    public ResponseEntity<?> updateInfo(
            @PathVariable UUID id,
            @RequestBody ProductCreationRequest productCreationRequest
    ) throws NoSuchElementException{
        try {
            return Response.createResponse(HttpStatus.OK, "update product successfully", productService.updateInfo(id, productCreationRequest));
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
    public ResponseEntity<?> addCategoryForProduct(
            @PathVariable UUID productId,
            @PathVariable UUID categoryId) {
        try {
            return Response.createResponse(HttpStatus.OK, "add category for product successfully", productService.addCategoryForProduct(productId, categoryId));
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
        }
    }
}
