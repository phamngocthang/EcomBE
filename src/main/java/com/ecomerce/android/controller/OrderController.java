package com.ecomerce.android.controller;

import com.ecomerce.android.dto.OrderDTO;
import com.ecomerce.android.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<?> save(@RequestBody OrderDTO order) {
        if(orderService.saveOrder(order)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Success");
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Error");
    }

    @GetMapping("/order")
    public ResponseEntity<?> getAllOrder() {
//        orderService.getAllOrder().forEach(System.out::println);
        return ResponseEntity.status(HttpStatus.OK).body(
                orderService.getAllOrder()
        );
    }
}
