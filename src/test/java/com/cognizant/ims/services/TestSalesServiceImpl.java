package com.cognizant.ims.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.ims.common.ResponseBean;
import com.cognizant.ims.dto.ProductDto;
import com.cognizant.ims.entities.Product;
import com.cognizant.ims.exception.ServiceException;
import com.cognizant.ims.respository.ProductRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TestSalesServiceImpl {
	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private SalesServiceImpl salesServiceImpl = new SalesServiceImpl();

	@Test
	public void testSellProduct() throws ServiceException {
		ResponseBean<ProductDto> response = new ResponseBean<>();

		Product existingProduct = new Product();
		existingProduct.setId(1);
		existingProduct.setName("Asus");
		existingProduct.setStock(100);

		ProductDto productDto = new ProductDto();
		productDto.setName(existingProduct.getName());
		productDto.setProductId(existingProduct.getId());
		productDto.setStock(existingProduct.getStock());

		when(productRepository.findById(productDto.getProductId())).thenReturn(Optional.of(existingProduct));
		salesServiceImpl.sellProduct(productDto.getProductId(), response);
		verify(productRepository, times(1)).save(Mockito.any());
		assertTrue(existingProduct.getStock() == 99);
	}

	@Test
	public void testSellProductOutOfStock() throws ServiceException {
		ResponseBean<ProductDto> response = new ResponseBean<>();

		Product existingProduct = new Product();
		existingProduct.setId(1);
		existingProduct.setName("Asus");
		existingProduct.setStock(0);

		ProductDto productDto = new ProductDto();
		productDto.setName(existingProduct.getName());
		productDto.setProductId(existingProduct.getId());
		productDto.setStock(existingProduct.getStock());

		when(productRepository.findById(productDto.getProductId())).thenReturn(Optional.of(existingProduct));
		salesServiceImpl.sellProduct(productDto.getProductId(), response);
		verify(productRepository, never()).save(Mockito.any());
		assertTrue(existingProduct.getStock() == productDto.getStock());
	}

	@Test
	public void testSellProductNotFound() throws ServiceException {
		ResponseBean<ProductDto> response = new ResponseBean<>();

		ProductDto productDto = new ProductDto();
		productDto.setProductId(2);

		when(productRepository.findById(productDto.getProductId())).thenReturn(Optional.empty());
		salesServiceImpl.sellProduct(productDto.getProductId(), response);
		verify(productRepository, never()).save(Mockito.any());
	}

}