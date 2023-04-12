package com.ecomerce.android.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.ecomerce.android.config.uploadFile.IStorageService;
import com.ecomerce.android.dto.BrandDTO;
import com.ecomerce.android.mapper.Mapper;
import com.ecomerce.android.responsitory.ProductReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomerce.android.model.Brand;
import com.ecomerce.android.responsitory.BrandReponsitory;
import com.ecomerce.android.service.BrandService;
import org.springframework.web.multipart.MultipartFile;


@Service
public class BrandServiceImpl implements BrandService {
	@Autowired
	BrandReponsitory brandReponsitory;

	@Autowired
	ProductReponsitory productReponsitory;

	@Autowired
	private IStorageService storageService;

	@Autowired
	private Mapper mapper;

	@Autowired
	Cloudinary cloudinary;

	@Override
	public List<BrandDTO> findAll() {
		List<Object[]> objs = productReponsitory.getAllBrand();
		List<BrandDTO> listBrandDTO = brandReponsitory.findAll().stream()
				.map(brand -> mapper.convertTo(brand, BrandDTO.class))
				.collect(Collectors.toList());

		IntStream.range(0, objs.size())
				.forEach(i -> {
					listBrandDTO.get(i).setProductAmount(Integer.valueOf(objs.get(i)[0].toString()));
				});
		return listBrandDTO;
	}

	@Override
	public Optional<Brand> findById(Integer id) {
		return brandReponsitory.findById(id);
	}

	@Override
	public Boolean insert(String name, MultipartFile file) throws IOException {
		List<Brand> listBrandByName = brandReponsitory.findByName(name);
		if(listBrandByName.size() >= 1) {
			return false;
		}

		Map r = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("public_id", name));
//		String url = cloudinary.url().transformation(new Transformation().width(600).height(500).crop("limit")).generate(name);
		String url = cloudinary.url().generate(name);

		Brand brand = Brand.builder()
				.name(name)
				.logo(url)
				.build();

		if(brandReponsitory.save(brand) != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
