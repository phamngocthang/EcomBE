package com.ecomerce.android.dto;

import com.ecomerce.android.model.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineitemDTO {
    private Integer lineItemId;
    private Integer quantity;
    private OptionDTO option;
}
