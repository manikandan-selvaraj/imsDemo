package com.cognizant.ims.controller;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cognizant.ims.dto.ProductDto;
import com.cognizant.ims.exception.ServiceException;
import com.cognizant.ims.services.PurchaseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@EnableWebMvc
public class TestPurchaseController {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@MockBean
	private PurchaseService purchaseService;

	@Before()
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void testAddProduct() throws Exception {
		ProductDto input = new ProductDto(1, "tests", "sample", 345, 678);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(input);
		MockHttpServletRequestBuilder request = post("/purchase/addProduct");
		request.content(json);
		request.accept(MediaType.APPLICATION_JSON);
		request.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void testAddProductsException() throws Exception {
		doThrow(new ServiceException()).when(purchaseService).addProduct(Mockito.any(), Mockito.any());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/purchase").accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().is4xxClientError());

	}

}