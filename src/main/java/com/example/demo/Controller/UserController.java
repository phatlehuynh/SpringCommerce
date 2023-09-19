package com.example.demo.Controller;

import com.example.demo.Model.Order;
import com.example.demo.Model.User;
import com.example.demo.Service.Implement.OrderService;
import com.example.demo.Utilities.ProductCreationRequest;
import com.example.demo.Utilities.Response;
import com.example.demo.Service.Implement.UserService;
import com.example.demo.Utilities.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @GetMapping("/users")
    public ResponseEntity<?> getAll() {
        return Response.createResponse(HttpStatus.OK, "get all user successfully", userService.getAll());
    }

    @GetMapping("/users/page")
    public ResponseEntity<?> getPage(
            @RequestParam(required = false) UUID userId,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<User> userPage = userService.getPage(pageIndex, pageSize);
        return Response.createResponse(HttpStatus.OK, "get page successfully", userPage.getContent());
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            User user = userService.getById(id);
            return Response.createResponse(HttpStatus.OK, "get user by id successfully", user);
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
        }
    }

//    @PutMapping("/user/updateinfo/{id}")
//    public ResponseEntity<?> updateInfo(
//            @PathVariable UUID id,
//            @RequestBody ProductCreationRequest productCreationRequest
//    ) throws NoSuchElementException{
//        try {
//            return Response.createResponse(HttpStatus.OK, "update product successfully", userService.updateInfo(id, productCreationRequest));
//        } catch (NoSuchElementException e) {
//            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
//
//        }
//    }


    @PutMapping("/users/{userId}/cart/addproduct")
    public ResponseEntity<?> addProductInCart(@PathVariable UUID userId, @RequestBody Map<UUID, Integer> productIntegerMap) {
        try {
            return Response.createResponse(HttpStatus.OK, "add product in user's cart successfully", userService.addProduct(userId, productIntegerMap));
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.OK, e.getMessage(), null);
        }
    }

    @PostMapping("/user/insert")
    public ResponseEntity<?> insert(@RequestBody User user) {
        userService.create(user);
        return Response.createResponse(HttpStatus.OK, "insert user successfully", user);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws NoSuchElementException {
        try {
            userService.deleteById(id);
            return Response.createResponse(HttpStatus.OK,
                    "deleted user have id: " + id.toString(),
                    null);

        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.OK, e.getMessage(), null);
        }
    }

    @PutMapping("/user/updateInfo/{id}")
    public ResponseEntity<?> updateInfo(@PathVariable UUID id, @RequestBody User newUser) {
        try {
            return Response.createResponse(HttpStatus.OK, "update user successfully", userService.updateInfo(id, newUser));
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);

        }
    }
}
