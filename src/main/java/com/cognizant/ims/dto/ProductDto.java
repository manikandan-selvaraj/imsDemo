package com.cognizant.ims.dto;

import java.io.Serializable;

public class ProductDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private int productId;
	private String name;
	private String description;
	private int stock;
	private int salesCount;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(int salesCount) {
		this.salesCount = salesCount;
	}

}
