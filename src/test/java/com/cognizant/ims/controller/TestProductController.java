package com.cognizant.ims.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cognizant.ims.dto.ProductDto;
import com.cognizant.ims.exception.ProductNotFoundException;
import com.cognizant.ims.exception.ServiceException;
import com.cognizant.ims.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TestProductController {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean
	private ProductService productService;

	@Before()
	public void setup() {
		// Init MockMvc Object and build
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void testGetSingleProduct() throws Exception {
		ProductDto prod = new ProductDto();
		prod.setProductId(1);
		prod.setName("HP");
		prod.setDescription("Desc");
		Mockito.when(productService.getProduct(Mockito.anyInt(), Mockito.any())).thenReturn(prod);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/1").accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void testGetAllProducts() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products/").accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void testUpdateProduct() throws Exception {
		ProductDto input = new ProductDto(2, "tests", "sample", 345, 678);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(input);
		doNothing().when(productService).updateProduct(Mockito.any(), Mockito.any());
		MockHttpServletRequestBuilder request = put("/products/updateProduct");
		request.content(json);
		request.accept(MediaType.APPLICATION_JSON);
		request.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void testDeleteProduct() throws Exception {
		doNothing().when(productService).deleteProduct(Mockito.anyInt(), Mockito.any());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/delete/1")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void testDeleteProductException() throws Exception {
		doThrow(new ServiceException()).when(productService).deleteProduct(Mockito.anyInt(), Mockito.any());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/delete/1")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void testDeleteProductProductNotFoundException() throws Exception {
		doThrow(new ProductNotFoundException()).when(productService).deleteProduct(Mockito.anyInt(), Mockito.any());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/delete/4")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

}
