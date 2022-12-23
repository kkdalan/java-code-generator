package com.fet.telemedicine.backend.dataaccess.dao;

import com.fet.telemedicine.backend.infrastructure.repository.jpa.AccountRepository;
import com.fet.telemedicine.backend.infrastructure.repository.jpa.po.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* Generated by Spring Data Generator on 23/12/2022
*/
@Component
public class AccountDao {

	private AccountRepository accountRepository;

	@Autowired
	public AccountDao(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

}