package com.ecomerce.android.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

	private Integer brandId;

	private String name;

	private String logo;

	private Integer productAmount;
}
