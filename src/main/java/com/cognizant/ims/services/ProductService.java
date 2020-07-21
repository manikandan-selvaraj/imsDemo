package com.cognizant.ims.services;

import java.util.List;

import com.cognizant.ims.common.ResponseBean;
import com.cognizant.ims.dto.ProductDto;
import com.cognizant.ims.exception.ProductNotFoundException;
import com.cognizant.ims.exception.ServiceException;

public interface ProductService {

	public List<ProductDto> getAllProducts(ResponseBean<List<ProductDto>> response) throws ServiceException;

	public ProductDto getProduct(Integer productId, ResponseBean<ProductDto> response) throws ServiceException;

	public void updateProduct(ProductDto productDto, ResponseBean<ProductDto> responseBean) throws ServiceException;

	public void deleteProduct(Integer productId, ResponseBean<ProductDto> response) throws ServiceException, ProductNotFoundException;
}
