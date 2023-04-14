package com.ecomerce.android.dto;

import com.ecomerce.android.model.Customer;
import com.ecomerce.android.model.Product;

import java.sql.Timestamp;

public class ReviewDTO {
    private Integer reviewId;

    private Integer rate;

    private String content;

    private Timestamp updateAt;

    private Customer customer;
}
