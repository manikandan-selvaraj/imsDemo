package com.cognizant.ims.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.ims.common.ResponseBean;
import com.cognizant.ims.dto.ProductDto;
import com.cognizant.ims.exception.ProductNotFoundException;
import com.cognizant.ims.services.ProductService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get all available products from the inventory.", notes = "This service returns all available products. "
			+ "\nStatus of the operation is mentioned in the field message")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseBean.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 500, message = "Failure") })
	public ResponseBean<List<ProductDto>> getAllProducts() {
		LOGGER.debug("Received request to return all available products");
		ResponseBean<List<ProductDto>> response = new ResponseBean<>();
		try {
			productService.getAllProducts(response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			response.setMessage(e.getMessage());
			response.setReturnCode(1);
		}
		return response;

	}

	@GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get details of a specific product from the inventory.", notes = "This call returns a particular product based on the product id given in the url. ")
	public ResponseBean<ProductDto> getProduct(@PathVariable Integer productId) {
		LOGGER.debug("Received request to return details of product with id {}", productId);
		ResponseBean<ProductDto> response = new ResponseBean<>();
		try {
			productService.getProduct(productId, response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			response.setMessage(e.getMessage());
			response.setReturnCode(1);
		}
		return response;
	}

	@PutMapping(value = "/updateProduct", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Updates an existing product in the inventory", notes = "This call updates the existing product's name and description by the product id ")
	public ResponseBean<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
		LOGGER.debug("Received request to update details of product with id {}", productDto.getProductId());
		ResponseBean<ProductDto> response = new ResponseBean<>();
		try {
			productService.updateProduct(productDto, response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			response.setMessage(e.getMessage());
			response.setReturnCode(1);
		}
		return response;
	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Deletes an existing product from the inventory", notes = "This call permanently removes the product from the inventory by the product id "
			+ "\nStatus of the operation is mentioned in the field message")
	public ResponseBean<ProductDto> deleteProduct(@PathVariable(value = "id") Integer productId) {
		LOGGER.debug("Received request to delete the product of id {}", productId);
		ResponseBean<ProductDto> response = new ResponseBean<>();
		try {
			productService.deleteProduct(productId, response);
		} catch (ProductNotFoundException prodNotFoundException) {
			LOGGER.error(prodNotFoundException.getMessage());
			response.setMessage(prodNotFoundException.getMessage());
			response.setReturnCode(1);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			response.setMessage(e.getMessage());
			response.setReturnCode(1);
		}
		return response;
	}

}
