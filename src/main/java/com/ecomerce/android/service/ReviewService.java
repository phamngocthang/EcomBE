package com.ecomerce.android.service;

import com.ecomerce.android.dto.ReviewDTO;
import com.ecomerce.android.model.Review;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getReviewsByProductId(Integer productId, String userName);

    ReviewDTO updateReview(Integer reviewId, Integer rate, String content);

    ReviewDTO insertReview(ReviewDTO reviewDTO);

    Boolean deleteReivew(Integer reviewId);
}
