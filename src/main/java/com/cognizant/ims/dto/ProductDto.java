package com.cognizant.ims.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private int productId;
	private String name;
	private String description;
	private int stock;
	private int salesCount;
}
