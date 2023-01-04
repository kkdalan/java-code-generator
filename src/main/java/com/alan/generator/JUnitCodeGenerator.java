package com.alan.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.alan.generator.junit.JUnitGenerator;

@JUnitGenerator(
	entityPackage = {"com.alan.generator.junit.testcase"},
        testCasePackage = "com.alan.generator.junit.testcase",
//        managerPackage = "com.alan.generator.junit.testcase.controller",
        testCasePostfix = "TestCase",
//        managerPostfix = "Test",
        additionalExtends = {},
        onlyAnnotations = false,
        debug = false,
        overwrite = true
)
@SpringBootApplication
public class JUnitCodeGenerator {

    public static void main(String[] args) {
	ConfigurableApplicationContext ctx = SpringApplication.run(JUnitCodeGenerator.class);
	ctx.close();
    }

}
