package com.cognizant.ims.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.ims.common.ResponseBean;
import com.cognizant.ims.dto.ProductDto;
import com.cognizant.ims.services.SalesService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sale")
public class SalesController {

	@Autowired
	private SalesService salesService;

	private static final Logger LOGGER = LoggerFactory.getLogger(SalesController.class);

	@PatchMapping("/{productId}")
	@ApiOperation(value = "Sell a product from the inventory.", notes = "This service updates the sales count and stock of a product. If the product does not exists throws an exception")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ResponseBean.class),
			@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 500, message = "Failure") })
	public ResponseBean<ProductDto> sellProduct(@PathVariable Integer productId) {
		LOGGER.debug("Received request to sell a product of id {}", productId);
		ResponseBean<ProductDto> response = new ResponseBean<>();
		try {
			salesService.sellProduct(productId, response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			response.setMessage(e.getMessage());
			response.setReturnCode(1);
		}
		return response;
	}

}
