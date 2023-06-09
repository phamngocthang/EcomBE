package com.ecomerce.android.controller;

import com.ecomerce.android.dto.ResponseObject;
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

    @GetMapping("/review/{productId}/{username}")
    public ResponseEntity<?> getReviewsByProductId(@PathVariable("productId") Integer productId,
                                                    @PathVariable("username") String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(
                reviewService.getReviewsByProductId(productId, userName)
        );
    }

    @PostMapping("/review")
    public ResponseEntity<?> insertReview(@RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(
                reviewService.insertReview(reviewDTO)
        );

    }
    @PutMapping("/review")
    public ResponseEntity<?> updateReview(@RequestParam("id") Integer reviewId,
                                          @RequestParam("rate") Integer rate,
                                          @RequestParam("content") String content) {
        return ResponseEntity.status(HttpStatus.OK).body(
                reviewService.updateReview(reviewId, rate, content)
        );
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") Integer reviewId) {
        if(reviewService.deleteReivew(reviewId)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Delete Product Successfully", "")
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("Failed", "Cannot find product to delete", "")
            );
        }
    }
}
