package com.ecomerce.android.service.impl;

import com.ecomerce.android.dto.ReviewDTO;
import com.ecomerce.android.mapper.Mapper;
import com.ecomerce.android.model.Review;
import com.ecomerce.android.responsitory.ReviewRepository;
import com.ecomerce.android.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private Mapper mapper;
    @Override
    public List<ReviewDTO> getReviewsByProductId(Integer productId, String userName) {
//        Sort sort = Sort.by(Sort.Direction.DESC, "updateAt");
//        return reviewRepository.findAll(sort)
//                .stream()
//                .map(review -> mapper.convertTo(review, ReviewDTO.class))
//                .collect(Collectors.toList());

        List<Review> getAllReviewByProductId = reviewRepository.getReviewByProductId(productId);
        if(userName != null && !userName.isEmpty()) {
            List<Review> sortedReview = getAllReviewByProductId.stream()
                    .filter(review -> review.getCustomer().getUserName().equals(userName))
                    .collect(Collectors.toList());

            List<Review> ortherReview = getAllReviewByProductId.stream()
                    .filter(review -> !review.getCustomer().getUserName().equals(userName))
                    .collect(Collectors.toList());
            sortedReview.addAll(ortherReview);

            return sortedReview.stream()
                    .map(review -> mapper.convertTo(review, ReviewDTO.class))
                    .collect(Collectors.toList());
        }

        return getAllReviewByProductId.stream()
                .map(review -> mapper.convertTo(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean updateReview(Integer reviewId, String content) {
        Optional<Review> isReview = reviewRepository.findById(reviewId);
        if(isReview.isPresent()) {
            Review newReview = isReview.get();
            newReview.setContent(content);
            reviewRepository.save(newReview);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Boolean insertReview(Review review) {
        return reviewRepository.save(review) != null ? true : false;
    }

    @Override
    public Boolean deleteReivew(Integer reviewId) {
        boolean isExistReview = reviewRepository.existsById(reviewId);
        if (isExistReview) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        else {
            return false;
        }
    }
}
