package com.cognizant.ims.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
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
import com.cognizant.ims.exception.ServiceException;
import com.cognizant.ims.services.PurchaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestPurchaseController {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@MockBean
	private PurchaseService purchaseService;

	// TODO update test case
	/*
	 * @Test
	 * 
	 * @Ignore
	 * 
	 * @WithMockUser(username = "admin", password = "admin") public void
	 * testAddProduct() throws Exception {
	 * 
	 * ProductDto product = new ProductDto(); product.setName("Dell");
	 * product.setDescription("desc"); ObjectMapper mapper = new ObjectMapper();
	 * mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false); ObjectWriter
	 * ow = mapper.writer().withDefaultPrettyPrinter(); String requestJson =
	 * ow.writeValueAsString(product);
	 * doNothing().when(purchaseService).addProduct(Mockito.any(), Mockito.any());
	 * 
	 * RequestBuilder requestBuilder =
	 * MockMvcRequestBuilders.post("/purchase").content(requestJson)
	 * .accept(MediaType.APPLICATION_JSON);
	 * mockMvc.perform(requestBuilder).andExpect(status().isOk()); }
	 * 
	 * @Test
	 * 
	 * @Ignore
	 * 
	 * @WithMockUser(username = "admin", password = "admin") public void
	 * testAddProductsException() throws Exception { doThrow(new
	 * ServiceException()).when(purchaseService).addProduct(Mockito.any(),
	 * Mockito.any());
	 * 
	 * RequestBuilder requestBuilder =
	 * MockMvcRequestBuilders.post("/purchase").accept(MediaType.APPLICATION_JSON);
	 * 
	 * MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	 * 
	 * assertTrue(result.getResponse().getStatus() == 500);
	 * 
	 * }
	 */

}