package com.ecomerce.android.service;

import com.ecomerce.android.dto.ReviewDTO;
import com.ecomerce.android.model.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getReviewsByProductId(Integer productId);

    Boolean updateReview(Integer reviewId, String content);

    Boolean insertReview(Review review);

    Boolean deleteReivew(Integer reviewId);
}
