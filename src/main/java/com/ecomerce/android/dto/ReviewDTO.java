package com.ecomerce.android.dto;

import com.ecomerce.android.model.Customer;
import com.ecomerce.android.model.Product;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Integer reviewId;

    private Integer rate;

    private String content;

    private Timestamp updateAt;

    private CustomerDTO customer;

    private ProductDTO product;
}
