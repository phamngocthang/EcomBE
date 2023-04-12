package com.ecomerce.android.responsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import com.ecomerce.android.model.Product;

import java.util.List;


@Repository
public interface ProductReponsitory extends JpaRepository<Product, Integer>{

    @Query(value = "select count(*) from Product p group by p.brand.brandId")
    List<Object[]> getAllBrand();

    @Query(value = "select p from Product p where p.brand.brandId=:brandId")
    List<Product> getProductByBrand(@Param("brandId") Integer brandId);

    @Query(value = "select p from Product p")
    List<Product> getAll();
}
