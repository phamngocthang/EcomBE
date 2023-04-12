package com.ecomerce.android.responsitory;

import com.ecomerce.android.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "select * from `order`", nativeQuery = true)
    public List<Order> selectAll();
}