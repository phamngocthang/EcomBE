package com.ecomerce.android.service;

import java.util.List;
import java.util.Optional;

import com.ecomerce.android.dto.BrandDTO;
import com.ecomerce.android.dto.ProductDTO;
import org.springframework.stereotype.Service;

import com.ecomerce.android.model.Product;

public interface ProductService {

	List<ProductDTO> findAll();

	ProductDTO findById(Integer id);


	List<ProductDTO>  getProductByBrand(Integer brandId);

	List<ProductDTO> getLastedProduct();

    List<ProductDTO> getPopularProduct();

    List<ProductDTO> getRelatedProduct(Integer productId);
}
