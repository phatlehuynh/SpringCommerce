package com.example.demo.Controller;

import com.example.demo.Model.Category;
import com.example.demo.Model.Comment;
import com.example.demo.Utilities.Response;
import com.example.demo.Service.Implement.CommentService;
import com.example.demo.Utilities.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    CommentService commentService;
    @GetMapping("/comments")
    public ResponseEntity<?> getAll() {
        return Response.createResponse(HttpStatus.OK, "get all comment successfully", commentService.getAll());
    }

    @GetMapping("/comments/page")
    public ResponseEntity<?> getPage(
            @RequestParam(required = false) UUID commentId,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Comment> commentPage = commentService.getPage(pageIndex, pageSize);
        return Response.createResponse(HttpStatus.OK, "get page successfully", commentPage.getContent());
    }


    @GetMapping("/comment/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            Comment comment = commentService.getById(id);
            return Response.createResponse(HttpStatus.OK, "get comment by id successfully", comment);
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
        }
    }

    @PostMapping("/comment/insert")
    public ResponseEntity<?> insert(@RequestBody Comment comment) {
        try {
            return Response.createResponse(HttpStatus.OK, "insert comment successfully", commentService.insert(comment));
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.OK, e.getMessage(), null);
        }
    }

    @DeleteMapping("/comment/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws NoSuchElementException {
        try {
            commentService.deleteById(id);
            return Response.createResponse(HttpStatus.OK,
                    "deleted comment have id: " + id.toString(),
                    null);

        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.OK, e.getMessage(), null);
        }
    }

    @PutMapping("/comment/updateInfo/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody String newStringComment) {
        try {
            return Response.createResponse(HttpStatus.OK, "update comment successfully", commentService.updateInfo(id, newStringComment));
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);

        }
    }


    @GetMapping("/comment/getByProductId/{productId}")
    public ResponseEntity<?> getByProductId(
            @PathVariable UUID productId,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Comment> categoriePage = commentService.getByProductId(productId, pageIndex, pageSize);
        PaginatedResponse<Comment> paginatedResponse = new PaginatedResponse<>(
                categoriePage.getContent(),
                categoriePage.getTotalElements(),
                categoriePage.getTotalPages()
        );
        return Response.createResponse(
                HttpStatus.OK, "get comment by product id: " + productId,
                paginatedResponse
        );
    }

}
