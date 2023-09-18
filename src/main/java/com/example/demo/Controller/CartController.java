package com.example.demo.Controller;

import com.example.demo.Model.Cart;
import com.example.demo.Utilities.CartCreationRequest;
import com.example.demo.Utilities.Response;
import com.example.demo.Service.Implement.CartService;
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
public class CartController {
    @Autowired
    CartService cartService;
    @GetMapping("/carts")
    public ResponseEntity<?> getAll() {
        return Response.createResponse(HttpStatus.OK, "get all cart successfully", cartService.getAll());
    }

    @GetMapping("/carts/page")
    public ResponseEntity<?> getPage(
            @RequestParam(required = false) UUID cartId,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Cart> cartPage = cartService.getPage(pageIndex, pageSize);
        return Response.createResponse(HttpStatus.OK, "get page successfully", cartPage.getContent());
    }


    @GetMapping("/cart/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            Cart cart = cartService.getById(id);
            return Response.createResponse(HttpStatus.OK, "get cart by id successfully", cart);
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
        }
    }

    @PostMapping("/cart/insert")
    public ResponseEntity<?> insert(@RequestBody CartCreationRequest cartCreationRequest) {
        return Response.createResponse(HttpStatus.OK, "insert cart successfully", cartService.insert(cartCreationRequest));
    }

    @DeleteMapping("/cart/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws NoSuchElementException {
        try {
            cartService.deleteById(id);
            return Response.createResponse(HttpStatus.OK,
                    "deleted cart have id: " + id.toString(),
                    null);

        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.OK, e.getMessage(), null);
        }
    }

    @PutMapping("/cart/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Cart newCart) {
        try {
            return Response.createResponse(HttpStatus.OK, "update cart successfully", cartService.update(id, newCart));
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);

        }
    }

}
