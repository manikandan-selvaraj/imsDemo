package com.cognizant.ims.services;

import java.util.Optional;

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
public class SalesServiceImpl implements SalesService {

	@Autowired
	private ProductRepository productRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(SalesServiceImpl.class);

	@Override
	public void sellProduct(Integer productId, ResponseBean<ProductDto> response) throws ServiceException {
		try {
			Optional<Product> optionalProduct = productRepository.findById(productId);
			if (optionalProduct.isPresent()) {
				Product product = optionalProduct.get();
				if (product.getStock() > 0) {
					int currentSalesCount = product.getSalesCount();
					int stock = product.getStock();
					product.setSalesCount(++currentSalesCount);
					product.setStock(--stock);
					productRepository.save(product);
					response.setMessage(
							String.format("Product of id %d is sold. Current stock is %d ", productId, stock));
					LOGGER.info("Product with id {} was sold successfully", productId);
				} else {
					response.setMessage(
							String.format("Product of id %d is out of stock.Please try agin later ", productId));
					LOGGER.info("Product of id {} is out of stock.", productId);
				}
			} else {
				response.setMessage(String.format("Product with id %d does not exists in the inventory", productId));
				LOGGER.info("Product with id {} does not exists in the inventory", productId);
			}

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
