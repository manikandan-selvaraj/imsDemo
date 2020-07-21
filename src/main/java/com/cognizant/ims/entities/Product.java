package com.cognizant.ims.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	private int stock;
	private int salesCount;

	public Product() {
		super();
	}

	public Product(int id, String name, String description, int stock, int salesCount) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.stock = stock;
		this.salesCount = salesCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
