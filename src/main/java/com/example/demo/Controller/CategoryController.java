package com.example.demo.Controller;

import com.example.demo.Model.Category;
import com.example.demo.Model.Product;
import com.example.demo.Response;
import com.example.demo.Service.Implement.CategoryService;
import com.example.demo.Service.Implement.CategoryService;
import com.example.demo.utilities.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("/categories")
    public ResponseEntity<?> getAll() {
        return Response.createResponse(HttpStatus.OK, "get all category successfully", categoryService.getAll());
    }

    @GetMapping("/categories/page")
    public ResponseEntity<?> getPage(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Category> categoryPage = categoryService.getPage(pageIndex, pageSize);
        return Response.createResponse(HttpStatus.OK, "get page successfully", categoryPage.getContent());
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            Category category = categoryService.getById(id);
            return Response.createResponse(HttpStatus.OK, "get category by id successfully", category);
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
        }
    }

    @PostMapping("/category/insert")
    public ResponseEntity<?> insert(@RequestBody Category category) {
        categoryService.create(category);
        return Response.createResponse(HttpStatus.OK, "insert category successfully", category);
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws NoSuchElementException {
        try {
            categoryService.deleteById(id);
            return Response.createResponse(HttpStatus.OK,
                    "deleted category have id: " + id.toString(),
                    null);

        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.OK, e.getMessage(), null);
        }
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Category newCategory) {
        try {
            return Response.createResponse(HttpStatus.OK, "update category successfully", categoryService.update(id, newCategory));
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);

        }
    }

    @GetMapping("/category/getByParentId/{parentId}")
    public ResponseEntity<?> getByParentId(
            @PathVariable UUID parentId,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Category> categoriePage = categoryService.getByParentId(parentId, pageIndex, pageSize);
        PaginatedResponse<Category> paginatedResponse = new PaginatedResponse<>(
                categoriePage.getContent(),
                categoriePage.getTotalElements(),
                categoriePage.getTotalPages()
        );
        return Response.createResponse(
                HttpStatus.OK, "get category by parent id: " + parentId,
                paginatedResponse
        );
    }
}
