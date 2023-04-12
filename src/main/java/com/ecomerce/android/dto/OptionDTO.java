package com.ecomerce.android.dto;

import com.ecomerce.android.model.Image;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionDTO {
    private Integer optionId;
    private String ram;

    private String rom;

    private Double price;

    private List<ImageDTO> images;
}
