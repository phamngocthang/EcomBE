package com.ecomerce.android.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ecomerce.android.dto.BrandDTO;
import com.ecomerce.android.dto.ProductDTO;
import com.ecomerce.android.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomerce.android.model.Product;
import com.ecomerce.android.responsitory.ProductReponsitory;
import com.ecomerce.android.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductReponsitory productReponsitory;
	@Autowired
	private Mapper productMapper;

	@Override
	public List<ProductDTO> findAll() {
		return productReponsitory.findAll()
				.stream()
				.map(product -> productMapper.convertTo(product, ProductDTO.class))
				.collect(Collectors.toList());
	}

	public ProductDTO findById(Integer id) {
		Optional<Product> product = productReponsitory.findById(id);
		if(product.isPresent()) {
			return productMapper.convertTo(product.get(), ProductDTO.class);
		}
		else {
			return null;
		}
	}

	@Override
	public List<BrandDTO> getAllBrand() {
		return null;
	}

	@Override
	public List<ProductDTO> getProductByBrand(Integer brandId) {
		return productReponsitory.getProductByBrand(brandId)
				.stream()
				.map(product -> productMapper.convertTo(product, ProductDTO.class))
				.collect(Collectors.toList());
	}
}
