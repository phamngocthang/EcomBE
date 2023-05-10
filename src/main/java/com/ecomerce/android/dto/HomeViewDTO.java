package com.ecomerce.android.dto;
import com.google.gson.annotations.SerializedName;

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
public class HomeViewDTO {
	
	
	private Integer id;
	
	@SerializedName("title")
	private String productName;
	
	private String image;
	
	private Double price;

}
