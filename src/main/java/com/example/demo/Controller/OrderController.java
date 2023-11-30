package com.example.demo.Controller;

import com.example.demo.Model.Order;
import com.example.demo.Service.Implement.OrderService;
import com.example.demo.Utilities.Response;
import com.example.demo.Utilities.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    OrderService orderService;

    @JsonView(Views.Public.class)
    @GetMapping("/orders")
    public ResponseEntity<?> getAll() {
        return Response.createResponse(HttpStatus.OK, "get all order successfully", orderService.getAll());
    }

    @JsonView(Views.Public.class)
    @GetMapping("/orders/page")
    public ResponseEntity<?> getPage(
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Order> orderPage = orderService.getPage(pageIndex, pageSize);
        return Response.createResponse(HttpStatus.OK, "get page successfully", orderPage.getContent());
    }

    @JsonView(Views.Public.class)
    @GetMapping("/order/getByUserId")
    public ResponseEntity<?> getByUserId(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Order> orderPage = orderService.getByUserId(userId, pageIndex, pageSize);
        return Response.createResponse(HttpStatus.OK, "get page successfully", orderPage.getContent());
    }

    @JsonView(Views.CartProduct.class)
    @GetMapping("/order/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            Order order = orderService.getById(id);
            return Response.createResponse(HttpStatus.OK, "get order by id successfully", order);
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
        }
    }

    @JsonView(Views.CartProduct.class)
    @PostMapping("/order/insert")
    public ResponseEntity<?> insert(
            @RequestBody Order order
    ) throws NoSuchElementException {
        try {
            return Response.createResponse(
                    HttpStatus.OK,
                    "insert order successfully",
                    orderService.insert(order)
            );
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
        }
    }

//    @DeleteMapping("/order/delete/{id}")
//    public ResponseEntity<?> delete(@PathVariable UUID id) throws NoSuchElementException {
//        try {
//            orderService.deleteById(id);
//            return Response.createResponse(HttpStatus.OK,
//                    "deleted order have id: " + id.toString(),
//                    null);
//
//        } catch (NoSuchElementException e) {
//            return Response.createResponse(HttpStatus.OK, e.getMessage(), null);
//        }
//    }

    @JsonView(Views.Public.class)
    @PutMapping("/order/updateStatus/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestParam byte newStatus) {
        try {
            return Response.createResponse(HttpStatus.OK, "update order status successfully", orderService.updateStatus(id, newStatus));
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);

        }
    }
}
