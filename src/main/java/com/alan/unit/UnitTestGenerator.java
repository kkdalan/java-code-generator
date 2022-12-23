package com.alan.unit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alan.unit.jpa.JPAGenerator;

@JPAGenerator(
        entityPackage = "com.fet.telemedicine.backend.repository.jdbc.po",
        repositoryPackage = "com.fet.telemedicine.backend.repository.jdbc",
//        managerPackage = "com.alan.batch.repository.jpa.meerkat.manager",
        repositoryPostfix = "Repository",
//        managerPostfix = "Manager",
        onlyAnnotations = false,
        debug = false,
        overwrite = true
)
@SpringBootApplication
public class UnitTestGenerator {

    public static void main(String[] args) {
	SpringApplication app = new SpringApplication(UnitTestGenerator.class);
	app.run(args);
    }

}
