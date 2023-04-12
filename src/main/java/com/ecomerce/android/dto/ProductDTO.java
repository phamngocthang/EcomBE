package com.ecomerce.android.dto;

import com.ecomerce.android.model.Brand;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private int productId;
    private String productName;
    private String cpu;
    private String description;
    private String origin;
    private String os;
    private String battery;

    private String screen;

    private Double price;

    private List<OptionDTO> options;
}
