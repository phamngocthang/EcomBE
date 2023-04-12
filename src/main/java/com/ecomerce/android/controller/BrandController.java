package com.ecomerce.android.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import com.ecomerce.android.config.uploadFile.IStorageService;
import com.ecomerce.android.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecomerce.android.service.BrandService;
import com.ecomerce.android.dto.BrandDTO;
import com.ecomerce.android.model.Brand;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class BrandController {
	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;


	@GetMapping("/brand")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(brandService.findAll());
	}


	@PostMapping(value = "/brand")
	public ResponseEntity<?> insert(@RequestParam("name") String name,
										@RequestParam("images") MultipartFile file) throws IOException {
		if(brandService.insert(name, file)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Success");
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Failed");
		}
	}
}
