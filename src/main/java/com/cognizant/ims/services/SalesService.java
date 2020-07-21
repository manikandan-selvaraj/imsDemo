package com.cognizant.ims.services;

import com.cognizant.ims.common.ResponseBean;
import com.cognizant.ims.dto.ProductDto;
import com.cognizant.ims.exception.ServiceException;

public interface SalesService {
	public void sellProduct(Integer productId, ResponseBean<ProductDto> response) throws ServiceException;
}
