package com.alan.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alan.generator.jpa.JPAGenerator;

@JPAGenerator(
        entityPackage = "com.fet.telemedicine.backend.infrastructure.repository.jpa.po",
        repositoryPackage = "com.fet.telemedicine.backend.infrastructure.repository.jpa",
        managerPackage = "com.fet.telemedicine.backend.dataaccess.dao",
        repositoryPostfix = "Repository",
        managerPostfix = "Dao",
        additionalExtends = {},
        onlyAnnotations = false,
        debug = false,
        overwrite = true
)
@SpringBootApplication
public class JPACodeGenerator {

    public static void main(String[] args) {
	SpringApplication.run(JPACodeGenerator.class);
    }

}
