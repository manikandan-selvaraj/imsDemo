package com.cognizant.ims.services;

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
public class TestPurchaseServiceImpl {
	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private PurchaseServiceImpl purchaseServiceImpl = new PurchaseServiceImpl();

	@Test
	public void testaddProduct() throws ServiceException {
		ResponseBean<ProductDto> response = new ResponseBean<>();
		ProductDto productDto = new ProductDto();
		productDto.setDescription("sample 1");
		productDto.setName("Dell");
		productDto.setProductId(1);
		productDto.setStock(500);
		when(productRepository.findByName(productDto.getName())).thenReturn(Optional.empty());
		purchaseServiceImpl.addProduct(productDto, response);
		verify(productRepository, times(1)).save(Mockito.any());
	}

	@Test
	public void testaddProductExistsAlready() throws ServiceException {
		ResponseBean<ProductDto> response = new ResponseBean<>();
		ProductDto productDto = new ProductDto();
		productDto.setDescription("sample 1");
		productDto.setName("Dell");
		productDto.setProductId(1);
		productDto.setStock(500);

		Product existingProduct = new Product();
		existingProduct.setId(1);
		existingProduct.setName(productDto.getName());
		existingProduct.setStock(100);
		when(productRepository.findByName(productDto.getName())).thenReturn(Optional.of(existingProduct));
		purchaseServiceImpl.addProduct(productDto, response);
		verify(productRepository, times(1)).save(Mockito.any());
	}

	@Test
	public void testaddProductEmptyName() throws ServiceException {
		ResponseBean<ProductDto> response = new ResponseBean<>();
		ProductDto productDto = new ProductDto();
		productDto.setName("");
		purchaseServiceImpl.addProduct(productDto, response);
		verify(productRepository, never()).findByName(productDto.getName());
	}
}