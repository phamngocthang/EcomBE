package com.ecomerce.android.controller;

import com.ecomerce.android.dto.ReviewDTO;
import com.ecomerce.android.model.Review;
import com.ecomerce.android.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/review/{productId}")
    public ResponseEntity<?> getReviewsByProductId(@PathVariable("productId") Integer productId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                reviewService.getReviewsByProductId(productId)
        );
    }

    @PostMapping("/review")
    public ResponseEntity<?> insertReview(@RequestBody Review review) {
        if(reviewService.insertReview(review)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "Success"
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    "Failed"
            );
        }
    }
    @PutMapping("/review/{id}/{content}")
    public ResponseEntity<?> updateReview(@PathVariable("id") Integer reviewId, @PathVariable("content") String content) {
        if(reviewService.updateReview(reviewId, content)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Success"
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    "Failed"
            );
        }
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") Integer reviewId) {
        if(reviewService.deleteReivew(reviewId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    "Success"
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    "Failed"
            );
        }
    }
}
