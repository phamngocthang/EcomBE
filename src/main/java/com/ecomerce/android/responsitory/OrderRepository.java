package com.ecomerce.android.responsitory;

import com.ecomerce.android.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT * FROM `orders` WHERE user_name =:username", nativeQuery = true)
    List<Order> getOrderByUsername(@Param("username") String username);


    @Query(value = "select MONTH(update_at), SUM(total_price) from orders Group by MONTH(update_at)", nativeQuery = true)
    List<Object[]> getBillInMonth();

    @Query(value = "select l.option.product.brand.name, count(*) From Lineitem l GROUP BY l.option.product.brand.brandId order by l.option.product.brand.brandId ASC")
    List<Object[]> getTotalPriceOfBrand();

    @Query(value = "SELECT brand.`name` From brand", nativeQuery = true)
    List<String> getAllBrandName();

    @Query(value = "select sum(o.totalPrice) from Order o WHERE YEAR(o.updateAt) = YEAR(CURRENT_DATE) and MONTH(o.updateAt) = MONTH(CURRENT_DATE)")
    Double getTotalPriceOrders();

    @Query(value = "select count(*) from User u WHERE YEAR(u.createdAt) = YEAR(CURRENT_DATE) and MONTH(u.createdAt) = MONTH(CURRENT_DATE)")
    Double countUsers();

    @Query(value = "select count(*) from Review r WHERE YEAR(r.updateAt) = YEAR(CURRENT_DATE) and MONTH(r.updateAt) = MONTH(CURRENT_DATE)")
    Double countReviews();

    @Query(value = "select count(*) from Product p WHERE YEAR(p.updateAt) = YEAR(CURRENT_DATE) and MONTH(p.updateAt) = MONTH(CURRENT_DATE)")
    Double countProducts();
}
