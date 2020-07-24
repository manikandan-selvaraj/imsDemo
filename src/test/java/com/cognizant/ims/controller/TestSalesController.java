package com.cognizant.ims.controller;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cognizant.ims.exception.ServiceException;
import com.cognizant.ims.services.SalesService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TestSalesController {
	@Autowired
	private MockMvc mockMvc;

	@Mock
	SalesService salesService;

	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void testSellProduct() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/sale/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk());

	}

	@Test
	@WithMockUser(username = "admin", password = "admin")
	public void testSellProductException() throws Exception {
		doThrow(new ServiceException()).when(salesService).sellProduct(Mockito.anyInt(), Mockito.any());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/sale/99").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isOk());

	}

}