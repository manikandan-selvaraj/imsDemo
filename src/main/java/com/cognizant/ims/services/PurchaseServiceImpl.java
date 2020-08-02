package com.cognizant.ims.services;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
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

	@Value("${sqs.url}")
	private String sqsUrl;

	private AmazonSQS amazonSqs;

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseServiceImpl.class);
	
	public PurchaseServiceImpl() {
		super();
	}

	@Autowired
	public PurchaseServiceImpl(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3Bucket) {
		this.amazonSqs = AmazonSQSClientBuilder.standard().withCredentials(awsCredentialsProvider).build();

		AmazonS3ClientBuilder.standard().withCredentials(awsCredentialsProvider).withRegion(awsRegion.getName())
				.build();
	}

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
				productDto.setProductId(product.getId());
				response.setData(productDto);
				response.setMessage(
						String.format("Stock of product %s is increased to %d ", productDto.getName(), newStock));
				LOGGER.info("Stock of product {} is increased to {} ", productDto.getName(), newStock);
			} else {
				Product newProduct = new Product();
				newProduct.setName(productDto.getName());
				newProduct.setDescription(productDto.getDescription());
				newProduct.setSalesCount(0);
				newProduct.setStock(productDto.getStock());
				productRepository.save(newProduct);
				productDto.setProductId(newProduct.getId());
				response.setData(productDto);
				response.setMessage("Product created successfully with id  " + newProduct.getId());
				LOGGER.info(" New product is created with id {} ", newProduct.getId());
			}

			this.amazonSqs.sendMessage(this.sqsUrl, response.getMessage());

		}

	}

}
