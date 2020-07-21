package com.cognizant.ims.services;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ims.common.ResponseBean;
import com.cognizant.ims.dto.ProductDto;
import com.cognizant.ims.entities.Product;
import com.cognizant.ims.exception.ServiceException;
import com.cognizant.ims.respository.ProductRepository;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private ProductRepository productRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseServiceImpl.class);

	@Override
	public void addProduct(ProductDto productDto, ResponseBean<ProductDto> response) throws ServiceException {
		if (StringUtils.isEmpty(productDto.getName())) {
			response.setMessage("Product name can not be empty.");
			LOGGER.warn("Product name can not be empty.");
		} else {
			Optional<Product> optionalProduct = productRepository.findByName(productDto.getName());
			if (optionalProduct.isPresent()) {
				LOGGER.debug("Product with name {} already exists in the inventory", productDto.getName());
				Product product = optionalProduct.get();
				int newStock = product.getStock() + productDto.getStock();
				product.setStock(newStock);
				productRepository.save(product);
				response.setData(productDto);
				response.setMessage(String.format("Stock of product %s is increased to %d ", productDto.getName(),
						productDto.getStock()));
				LOGGER.info("Stock of product {} is increased to {} ", productDto.getName(), newStock);
			} else {
				Product newProduct = new Product();
				newProduct.setName(productDto.getName());
				newProduct.setDescription(productDto.getDescription());
				newProduct.setSalesCount(0);
				newProduct.setStock(productDto.getStock());
				productRepository.save(newProduct);
				response.setData(productDto);
				response.setMessage("Product created successfully with id  " + newProduct.getId());
				LOGGER.info(" New product is created with id {} ", newProduct.getId());
			}
		}

	}

}
