package com.ecomerce.android.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ecomerce.android.dto.BrandDTO;
import com.ecomerce.android.dto.HomeViewDTO;
import com.ecomerce.android.dto.ProductDTO;
import org.springframework.stereotype.Service;

import com.ecomerce.android.model.Product;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

	List<ProductDTO> findAll();

	ProductDTO findById(Integer id);

	List<ProductDTO>  getProductByBrand(Integer brandId);

	List<HomeViewDTO> getLastedProduct();

    List<HomeViewDTO> getPopularProduct();

    List<ProductDTO> getRelatedProduct(Integer productId);

    List<ProductDTO> searchProduct(String keyword);

    List<ProductDTO> filterProduct(double startPrice, double endPrice,
                                   int startBattery, int endBattery,
                                   double startScreen, double endScreen);

    Boolean updateImage(Integer id, MultipartFile file) throws IOException;
}
