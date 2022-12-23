package com.alan.generator.junit.testcase.target;

import org.springframework.stereotype.Component;

@Component
public class User {

    private String id;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

}
