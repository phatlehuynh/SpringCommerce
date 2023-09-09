package com.example.demo.Controller;

import com.example.demo.Model.Category;
import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Response;
import com.example.demo.Service.Implement.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


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
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
            Page<Product> productPage = productService.getPage(pageIndex, pageSize);
            return Response.createResponse(HttpStatus.OK, "get page successfully", productPage.getContent());
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
    public ResponseEntity<?> insert(@RequestBody Product product) {
        productService.create(product);
        return Response.createResponse(HttpStatus.OK, "insert product successfully", product);
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

    @PostMapping("/test")
    public Product test() {
        List<Category> categories = new ArrayList<>();
        Category categoryA = new Category("category a", null, null);
        categories.add(categoryA);
        Product productA = new Product();
        productA.setTitle("prduct a");
        productA.setCategories(categories);
        return productService.create(productA);
    }

    @GetMapping("/test2/{id}")
    public List<Product> test2(@PathVariable UUID id) {
        try {
            Product product = productService.findById(id);
            List<Product> productList = new ArrayList<>();
            productList.add(product);
            return productList;
        } catch (NoSuchElementException e) {
            // Xử lý ngoại lệ ở đây, ví dụ: in ra log và trả về danh sách rỗng hoặc null
            e.printStackTrace();
            return new ArrayList<>(); // Hoặc return null;
        }
    }

    @GetMapping("/test3/{id}")
    public  ResponseEntity<?> test3(@PathVariable UUID id) {
        Product product = productService.findById(id);
        return Response.createResponse(HttpStatus.OK, "ok", product.getCategories());
    }







}
