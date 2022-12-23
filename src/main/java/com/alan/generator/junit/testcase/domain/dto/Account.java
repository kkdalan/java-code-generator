package com.alan.generator.junit.testcase.domain.dto;

import java.util.Date;

public class Account {

    private String name;

    private String password;

    private String salt;

    private Integer status;

    private Date activateTime;

    private Byte validFlag;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getSalt() {
	return salt;
    }

    public void setSalt(String salt) {
	this.salt = salt;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public Date getActivateTime() {
	return activateTime;
    }

    public void setActivateTime(Date activateTime) {
	this.activateTime = activateTime;
    }

    public Byte getValidFlag() {
	return validFlag;
    }

    public void setValidFlag(Byte validFlag) {
	this.validFlag = validFlag;
    }

}
