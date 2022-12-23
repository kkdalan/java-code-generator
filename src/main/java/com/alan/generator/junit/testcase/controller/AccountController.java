package com.alan.generator.junit.testcase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alan.generator.junit.testcase.domain.dto.Account;
import com.alan.generator.junit.testcase.service.AccountService;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/save")
    public ResponseEntity save(@RequestBody Account record) {
	return ResponseEntity.ok(null);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity delete(@RequestBody List<Account> records) {
	return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity findAll() {
	return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/findByName")
    public ResponseEntity findByName(@RequestParam String name) {
	Account account = accountService.findByName(name);
	return ResponseEntity.ok(account);
    }

    @PostMapping(value = "/findPage")
    public ResponseEntity findPage(@RequestBody PageRequest pageRequest) {
	return ResponseEntity.ok(null);
    }

}
