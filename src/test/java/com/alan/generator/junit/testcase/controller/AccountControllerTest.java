package com.alan.generator.junit.testcase.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alan.generator.junit.testcase.base.BaseControllerTest;
import com.alan.generator.junit.testcase.domain.dto.Account;
import com.alan.generator.junit.testcase.service.AccountService;

@WebMvcTest(controllers = AccountController.class)
public class AccountControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;

    @Test
    public void givenAccountUserProvided_whenFindByName_thenReturns200() throws Exception {
	// GIVEN
	Account account = null;
	Mockito.when(accountService.findByName(Mockito.anyString())).thenReturn(account);
	// WHEN
	RequestBuilder reqeust = MockMvcRequestBuilders.get("/account/findByName")
		.contentType(MediaType.APPLICATION_JSON)
		.param("name", "alanhuang");
	// THEN
	mockMvc.perform(reqeust).andExpect(MockMvcResultMatchers.status().isOk());
    }

 

}