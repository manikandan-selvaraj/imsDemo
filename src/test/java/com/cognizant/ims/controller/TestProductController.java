package com.cognizant.ims.controller;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cognizant.ims.dto.ProductDto;
import com.cognizant.ims.services.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestProductController {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@MockBean
	private ProductService productService;

	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void testGetAllProducts() throws Exception {
		ProductDto prod = new ProductDto();
		prod.setProductId(1);
		prod.setName("HP");
		prod.setDescription("Desc");
		Mockito.when(productService.getProduct(Mockito.anyInt(), Mockito.any())).thenReturn(prod);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/1").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertTrue(result.getResponse().getStatus()==200);

	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void testGetAllProductsException() throws Exception {
		ProductDto prod = new ProductDto();
		prod.setProductId(1);
		prod.setName("HP");
		prod.setDescription("Desc");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/d").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertTrue(result.getResponse().getStatus()==400);

	}


}
