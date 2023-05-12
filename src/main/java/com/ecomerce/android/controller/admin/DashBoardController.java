package com.ecomerce.android.controller.admin;

import com.ecomerce.android.responsitory.OrderRepository;
import com.ecomerce.android.service.DashBoardService;
import com.ecomerce.android.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/admin")
public class DashBoardController {
    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping("/dashboard/get-bill-in-month")
    public ResponseEntity<TreeMap<Integer, Double>> getBillInMonth() {
        return ResponseEntity.status(HttpStatus.OK).body(dashBoardService.getBillInMonth());
    }

    @GetMapping("/dashboard/get-total-price-of-brand")
    public ResponseEntity<HashMap<String, Double>> getTotalPriceOfBrand() {
        return ResponseEntity.status(HttpStatus.OK).body(dashBoardService.getTotalPriceOfBrand());
    }

    @GetMapping("/dashboard/get-statistics")
    public ResponseEntity<HashMap<String, Double>> getStatistics() {
        return ResponseEntity.status(HttpStatus.OK).body(dashBoardService.getStatistics());
    }
}
