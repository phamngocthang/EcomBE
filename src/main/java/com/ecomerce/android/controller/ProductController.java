package com.ecomerce.android.controller;

import java.io.IOException;
import java.util.Optional;

import com.ecomerce.android.dto.ProductDTO;
import com.ecomerce.android.dto.ResponseObject;
import com.ecomerce.android.model.Product;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecomerce.android.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductService productService;
	private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

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
//	@GetMapping(value = "/product/get")
//	public ResponseEntity<?> findById(@RequestParam("id") Integer productId) {
//		ProductDTO productDTO = productService.findById(productId);
//		if(productDTO != null) {
//			return ResponseEntity.status(HttpStatus.OK).body(
//					new ResponseObject("Success", "Find Product Successfully", productDTO)
//			);
//		}
//		else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//					new ResponseObject("Failed", "Not Product In DB", "")
//			);
//		}
//	}
	@GetMapping(value = "/product/get")
	public ResponseEntity<?> findById(@RequestParam("id") Integer productId) {
		ProductDTO productDTO = productService.findById(productId);
		if(productDTO != null) {
			return ResponseEntity.status(HttpStatus.OK).body(productDTO);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ResponseObject("Failed", "Not Product In DB", "")
			);
		}
	}

	@GetMapping(value = "/product/related-product")
	public ResponseEntity<?> getRelatedProduct(@RequestParam("id") Integer productId) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.getRelatedProduct(productId));
	}

	@GetMapping(value = "/product/lasted-product")
	public ResponseEntity<?> getLastedProduct() {
		return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(productService.getLastedProduct()));
	}

	@GetMapping(value = "/product/popular-product")
	public ResponseEntity<?> getPopularProduct() {
		return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(productService.getPopularProduct()));
	}

	@GetMapping(value = "/product/search")
	public ResponseEntity<?> searchProduct(@RequestParam("keyword") String keyword) {
		if(productService.searchProduct(keyword) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ResponseObject("Failed", "Not Product In DB", "")
			);
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject("Success", "Find Customer Successfully",productService.searchProduct(keyword))
			);
		}
	}

	@GetMapping(value = "/product/filter")
	public ResponseEntity<?> filterProduct(@RequestParam("start-price") double startPrice,
										   @RequestParam("end-price") double endPrice,
										   @RequestParam("start-battery") int startBattery,
										   @RequestParam("end-battery") int endBattery,
										   @RequestParam("start-screen") double startScreen,
										   @RequestParam("end-screen") double endScreen
	) {
		return ResponseEntity.status(HttpStatus.OK).body(
				productService.filterProduct(startPrice, endPrice, startBattery, endBattery, startScreen, endScreen)
		);
	}

	@PutMapping(value = "/product/update-img")
	public ResponseEntity<?> update(@RequestParam("id") Integer optionId,
									@RequestParam("images") MultipartFile file) throws IOException {
		if(productService.updateImage(optionId, file)) {
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject("Success", "Update Brand Successfully", "")
			);
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new ResponseObject("Failed", "Brand has already in DB", "")
			);
		}
	}
}
