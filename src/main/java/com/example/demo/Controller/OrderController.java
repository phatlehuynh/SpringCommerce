package com.example.demo.Controller;

import com.example.demo.Model.Order;
import com.example.demo.Utilities.OrderCreationRequest;
import com.example.demo.Utilities.ProductCreationRequest;
import com.example.demo.Utilities.Response;
import com.example.demo.Service.Implement.OrderService;
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
    @GetMapping("/orders")
    public ResponseEntity<?> getAll() {
        return Response.createResponse(HttpStatus.OK, "get all order successfully", orderService.getAll());
    }

    @GetMapping("/orders/page")
    public ResponseEntity<?> getPage(
            @RequestParam(required = false) UUID orderId,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Order> orderPage = orderService.getPage(pageIndex, pageSize);
        return Response.createResponse(HttpStatus.OK, "get page successfully", orderPage.getContent());
    }


    @GetMapping("/order/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        try {
            Order order = orderService.getById(id);
            return Response.createResponse(HttpStatus.OK, "get order by id successfully", order);
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);
        }
    }

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

    @DeleteMapping("/order/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) throws NoSuchElementException {
        try {
            orderService.deleteById(id);
            return Response.createResponse(HttpStatus.OK,
                    "deleted order have id: " + id.toString(),
                    null);

        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.OK, e.getMessage(), null);
        }
    }

    @PutMapping("/order/updateStatus/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestParam byte newStatus) {
        try {
            return Response.createResponse(HttpStatus.OK, "update order status successfully", orderService.updateStatus(id, newStatus));
        } catch (NoSuchElementException e) {
            return Response.createResponse(HttpStatus.NOT_FOUND, e.getMessage(), null);

        }
    }
}
