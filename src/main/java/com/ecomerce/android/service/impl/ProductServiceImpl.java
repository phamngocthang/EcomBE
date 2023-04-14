package com.ecomerce.android.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ecomerce.android.dto.BrandDTO;
import com.ecomerce.android.dto.ProductDTO;
import com.ecomerce.android.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
	public List<ProductDTO> getProductByBrand(Integer brandId) {
		return productReponsitory.getProductByBrand(brandId)
				.stream()
				.map(product -> productMapper.convertTo(product, ProductDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getLastedProduct() {
		Sort sort = Sort.by(Sort.Direction.DESC, "productId");
		return productReponsitory.findAll(sort)
				.stream()
				.limit(6)
				.map(product -> productMapper.convertTo(product, ProductDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getPopularProduct() {
		return productReponsitory.getPopularProduct()
				.stream()
				.map(product -> productMapper.convertTo(product, ProductDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getRelatedProduct(Integer productId) {
		// Lay cac san pham cung brand va duoc mua nhieu nhat.
		// Neu khong du 6 sp lay them sp cung brand
		Integer brandId = productReponsitory.findById(productId).get().getBrand().getBrandId();
		List<Product> listRelatedProduct = productReponsitory.getRelatedProduct(brandId);
		if(listRelatedProduct.size() < 6) {
			List<Product> listProductByBrand = productReponsitory.getProductByBrand(brandId);
			for(Product p : listProductByBrand) {
				boolean exist = listRelatedProduct
						.stream()
						.anyMatch(product -> product.getProductId() == p.getProductId());
				if(!exist) {
					listRelatedProduct.add(p);
				}
				if(listRelatedProduct.size() >= 6)
					break;
			}
		}

		return listRelatedProduct.stream()
				.map(product -> productMapper.convertTo(product, ProductDTO.class))
				.collect(Collectors.toList());
	}
}
