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

    @Query(value = "select o.product from Option o, Lineitem l where o.optionId = l.option.optionId GROUP BY " +
            "o.product.productId order by count(o.product.productId) DESC, o.product.productId ASC")
    List<Product> getPopularProduct();

    @Query(value = "select l.option.product from Lineitem l where l.option.product.brand.brandId = ?1 group by " +
            "l.option.product.productId order by count(l.option.product.productId) DESC")
    List<Product> getRelatedProduct(Integer brandId);

    @Query(value = "select p from Product p where p.brand.brandId=:brandId")
    List<Product> getProductByBrand(@Param("brandId") Integer brandId);

    List<Product> findByPriceBetween(Double startPrice, Double endPrice);

    @Query("select p from Product p where CAST(SUBSTRING(p.battery, 1, 4) AS integer ) >= ?1 AND CAST(SUBSTRING(p.battery, 1, 4) AS integer ) <= ?2")
    List<Product> findByBatteryRange(Integer minBattery, Integer maxBattery);

    @Query("select p from Product p where CAST(SUBSTRING(p.screen, 1, 3) AS double ) >= ?1 AND CAST(SUBSTRING(p.battery, 1, 4) AS double ) <= ?2")
    List<Product> findByScreenSizeRange(double minScreenSize, double maxScreenSize);
}
