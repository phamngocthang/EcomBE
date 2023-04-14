package com.ecomerce.android.responsitory;

import com.ecomerce.android.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select r from Review r where r.product.productId = ?1 order by r.updateAt DESC")
    List<Review> getReviewByProductId(Integer productId);
}
