package com.alan.generator.junit.testcase.service;

import com.alan.generator.junit.testcase.domain.dto.Account;

public interface AccountService {

    Account findByName(String name);

}
