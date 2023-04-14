package com.ecomerce.android.responsitory;

import com.ecomerce.android.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
