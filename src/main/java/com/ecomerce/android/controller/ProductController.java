package com.ecomerce.android.controller;

import java.util.Optional;

import com.ecomerce.android.dto.ProductDTO;
import com.ecomerce.android.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecomerce.android.service.ProductService;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductService productService;

	// Lấy tất cả sản phẩm theo brandId
	@GetMapping(value = "/product/{brandId}")
	public ResponseEntity<?> getAllProductByBrand(@PathVariable("brandId") Integer brandId) {
		return ResponseEntity.status(HttpStatus.OK).body(
				productService.getProductByBrand(brandId)
		);
	};
	// Lấy tất cả sản phẩm
	@GetMapping( "/product")
	public ResponseEntity<?> getAllProduct() {
		return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
	}

	// Lấy 1 sản phẩm theo Id sản phẩm
	@GetMapping(value = "/product/get")
	public ResponseEntity<?> findById(@RequestParam("id") Integer productId) {
		ProductDTO productDTO = productService.findById(productId);
		if(productDTO != null) {
			return ResponseEntity.status(HttpStatus.OK).body(productDTO);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductId not exist");
		}
	}
}
