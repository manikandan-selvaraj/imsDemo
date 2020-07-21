package com.cognizant.ims.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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
import com.cognizant.ims.exception.ProductNotFoundException;
import com.cognizant.ims.exception.ServiceException;
import com.cognizant.ims.respository.ProductRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TestProductServiceImpl {
	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductServiceImpl productServiceImpl = new ProductServiceImpl();

	@Test
	public void testGetAllProducts() throws ServiceException {
		ResponseBean<List<ProductDto>> response = new ResponseBean<>();
		Product prod1 = new Product();
		prod1.setDescription("sample 1");
		prod1.setName("Dell");
		Product prod2 = new Product();
		prod2.setDescription("sample 2");
		prod2.setName("Acer");

		List<Product> sampleList = new ArrayList<>();
		sampleList.add(prod1);
		sampleList.add(prod2);

		Iterable<Product> testProduct = sampleList;
		when(productRepository.findAll()).thenReturn(testProduct);
		productServiceImpl.getAllProducts(response);
		List<ProductDto> productList = response.getData();
		assertTrue(productList.size() == 2);
	}

	@Test
	public void testGetProduct() throws ServiceException {
		ResponseBean<ProductDto> response = new ResponseBean<>();
		Product prod1 = new Product();
		prod1.setDescription("sample 1");
		prod1.setName("Dell");
		prod1.setId(1);
		Optional<Product> optProduct = Optional.of(prod1);

		when(productRepository.findById(1)).thenReturn(optProduct);
		productServiceImpl.getProduct(prod1.getId(), response);
		assertEquals(prod1.getId(), response.getData().getProductId());
	}

	@Test
	public void testGetProductNoResult() throws ServiceException {
		ResponseBean<ProductDto> response = new ResponseBean<>();
		Product prod1 = new Product();
		prod1.setDescription("sample 1");
		prod1.setName("Dell");
		prod1.setId(1);

		when(productRepository.findById(prod1.getId())).thenReturn(Optional.empty());
		productServiceImpl.getProduct(prod1.getId(), response);
		assertTrue(response.getData() == null);
	}

	@Test
	public void testGetProductException() throws ServiceException {
		assertThrows(ServiceException.class, () -> {
			productServiceImpl.getProduct(null, null);
		});
	}

	@Test
	public void testUpdateProduct() throws ServiceException {

		ResponseBean<ProductDto> response = new ResponseBean<>();
		ProductDto productDto = new ProductDto();
		productDto.setDescription("sample 1");
		productDto.setName("Dell");
		productDto.setProductId(1);
		productDto.setStock(500);

		Product product = new Product();
		product.setId(productDto.getProductId());
		product.setName(productDto.getName());
		product.setStock(0);

		Optional<Product> optProduct = Optional.of(product);
		when(productRepository.findById(1)).thenReturn(optProduct);
		when(productRepository.findByName(productDto.getName())).thenReturn(Optional.empty());
		productServiceImpl.updateProduct(productDto, response);
		assertTrue(response.getData().getStock() == productDto.getStock());
	}

	@Test
	public void testUpdateProductDuplicateName() throws ServiceException {
		ResponseBean<ProductDto> response = new ResponseBean<>();
		ProductDto productDto = new ProductDto();
		productDto.setDescription("sample 1");
		productDto.setName("Dell");
		productDto.setProductId(1);
		productDto.setStock(500);

		Product product = new Product();
		product.setId(productDto.getProductId());
		product.setName(productDto.getName());
		product.setDescription("Old Description");
		product.setStock(0);
		Product duplicateProduct = new Product();
		duplicateProduct.setId(2);
		duplicateProduct.setName(productDto.getName());

		Optional<Product> optProduct = Optional.of(product);
		Optional<Product> dupProduct = Optional.of(duplicateProduct);
		when(productRepository.findById(1)).thenReturn(optProduct);
		when(productRepository.findByName(productDto.getName())).thenReturn(dupProduct);
		productServiceImpl.updateProduct(productDto, response);
		assertFalse(response.getData().getDescription().equalsIgnoreCase(product.getDescription()));
	}

	@Test
	public void testUpdateProductIdNotPresent() throws ServiceException {
		ResponseBean<ProductDto> response = new ResponseBean<>();
		ProductDto productDto = new ProductDto();
		productDto.setDescription("sample 1");
		productDto.setName("Dell");
		productDto.setProductId(9);
		productDto.setStock(500);

		when(productRepository.findById(9)).thenReturn(Optional.empty());
		productServiceImpl.updateProduct(productDto, response);
		verify(productRepository, never()).save(Mockito.any());

	}

	

	@Test
	public void testDeleteProduct() throws ServiceException, ProductNotFoundException {
		ResponseBean<ProductDto> response = new ResponseBean<>();
		Product product = new Product();
		product.setDescription("sample 1");
		product.setName("Dell");
		product.setId(9);
		product.setStock(500);

		productServiceImpl.deleteProduct(product.getId(), response);
		verify(productRepository, times(1)).deleteById(product.getId());
	}

	@Test
	public void testDeleteProductIdIsNull() throws ServiceException, ProductNotFoundException {
		ResponseBean<ProductDto> response = new ResponseBean<>();

		productServiceImpl.deleteProduct(null, response);
		verify(productRepository, never()).deleteById(null);
	}

}
