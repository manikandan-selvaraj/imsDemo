package com.cognizant.ims.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cognizant.ims.common.ResponseBean;
import com.cognizant.ims.dto.ProductDto;
import com.cognizant.ims.entities.Product;
import com.cognizant.ims.exception.ProductNotFoundException;
import com.cognizant.ims.exception.ServiceException;
import com.cognizant.ims.respository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Override
	public List<ProductDto> getAllProducts(ResponseBean<List<ProductDto>> response) throws ServiceException {
		try {
			List<Product> productList = (List<Product>) productRepository.findAll();
			List<ProductDto> productDtoList = new ArrayList<>();
			if (!productList.isEmpty()) {
				productDtoList = productList.parallelStream().map(this::convertProductToDto)
						.collect(Collectors.toList());
				response.setData(productDtoList);
				response.setMessage(productDtoList.size() + " products retrieved from the inventory");
			}
			LOGGER.info("Retrieving {} products", productList.size());
			return productDtoList;
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	// @Cacheable(value = "productCache", key = "#p0")
	public ProductDto getProduct(Integer productId, ResponseBean<ProductDto> response) throws ServiceException {
		try {
			Optional<Product> optionalProduct = productRepository.findById(productId);
			ProductDto productDto = new ProductDto();
			if (optionalProduct.isPresent()) {
				productDto = convertProductToDto(optionalProduct.get());
				response.setData(productDto);
				response.setMessage(String.format("Product with id %d retrieved successfully.", productId));
			} else {
				response.setMessage(String.format("Product with id %d does not exists.", productId));
			}
			return productDto;
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	private ProductDto convertProductToDto(Product product) {
		ProductDto productDto = new ProductDto();

		productDto.setProductId(product.getId());
		productDto.setName(product.getName());
		productDto.setDescription(product.getDescription());
		productDto.setStock(product.getStock());
		productDto.setSalesCount(product.getSalesCount());
		return productDto;
	}

	@Override
	public void updateProduct(ProductDto productDto, ResponseBean<ProductDto> response) throws ServiceException {
		try {
			Optional<Product> optionalProduct = productRepository.findById(productDto.getProductId());
			if (optionalProduct.isPresent()) {
				Product product = optionalProduct.get();
				Optional<Product> existingOptionalProduct = productRepository.findByName(productDto.getName());
				// check for duplicate group name
				if (existingOptionalProduct.isPresent()
						&& (productDto.getProductId() != existingOptionalProduct.get().getId())) {
					LOGGER.info("Product with name {} already exists", productDto.getName());
					response.setMessage(String.format("Product with name %s already exists", productDto.getName()));
				} else {
					product.setName(productDto.getName());
					product.setDescription(productDto.getDescription());
					productRepository.save(product);
					response.setMessage(
							String.format("Product with id %d updated successfully", productDto.getProductId()));
				}
				response.setData(productDto);

			} else {
				LOGGER.info("Product with id {} does not exists", productDto.getProductId());
				response.setMessage(String.format("Product with id %d does not exists", productDto.getProductId()));
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void deleteProduct(Integer productId, ResponseBean<ProductDto> response)
			throws ServiceException, ProductNotFoundException {
		try {
			if (productId != null) {
				productRepository.deleteById(productId);
				response.setMessage("Product deleted from the inventory successfully.");
			} else {
				response.setMessage("Product id should not be null.");
			}
		} catch (EmptyResultDataAccessException emptyDataException) {
			throw new ProductNotFoundException("Product with given id does not exists" );
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

}
