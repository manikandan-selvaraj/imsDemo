package com.cognizant.ims.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.ims.common.ResponseBean;
import com.cognizant.ims.dto.ProductDto;
import com.cognizant.ims.services.PurchaseService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseController.class);

	@PostMapping("/addProduct")
	@ApiOperation(value = "Add new product to the inventory.", notes = "This service adds a new product to the inventory."
			+ "\nStatus of the operation is mentioned in the field message")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseBean.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 500, message = "Failure") })
	public ResponseBean<ProductDto> addProduct(@RequestBody ProductDto productDto) {
		LOGGER.debug("Received request to create a new product");
		ResponseBean<ProductDto> response = new ResponseBean<>();
		try{
			purchaseService.addProduct(productDto, response);
		}catch (Exception e) {
			LOGGER.error(e.getMessage());
			response.setMessage(e.getMessage());
			response.setReturnCode(1);
		}
		return response;
	}

}
