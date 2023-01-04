package com.alan.generator.junit.structure;

import java.lang.reflect.Field;
import java.util.Set;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alan.generator.common.support.BuildHelper;
import com.alan.generator.common.support.JavaCodeBuilder;
import com.alan.generator.common.support.ObjectStructure;
import com.alan.generator.common.support.ObjectStructure.ObjectMethod;
import com.alan.generator.common.util.GeneratorUtil;
import com.alan.generator.junit.testcase.domain.dto.Account;
import com.cmeza.sdgenerator.support.maker.values.ObjectTypeValues;
import com.cmeza.sdgenerator.support.maker.values.ScopeValues;
import com.cmeza.sdgenerator.util.CustomResourceLoader;

public class ControllerTestCaseStructure extends JUnitTestCaseStructure {

    private String givenClause = "// TODO";
    private String whenClause = "// TODO";
    private String thenClause = "// TODO";
    
    public ControllerTestCaseStructure(String testCasePackage, String entityName, String entityClass, String postfix,
	    CustomResourceLoader loader, Set<String> additionalExtends) {
	super(testCasePackage, entityName, entityClass, postfix, loader, additionalExtends);
    }
    
    @Override
    protected void buildJavaCode(String testCasePackage, String entityName, String entityClass, String postfix, CustomResourceLoader loader, Set<String> additionalExtends) {
  	String testCaseName = entityName + postfix;
	
  	ObjectStructure objectStructure = new ObjectStructure(testCasePackage, ScopeValues.PUBLIC, ObjectTypeValues.CLASS, testCaseName)
                  .addImport(entityClass)
                  .addImport("org.junit.jupiter.api.Test")
                  .addImport("org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest")
                  .addImport("org.springframework.test.web.servlet.MockMvc")
                  .addImport(Autowired.class)
                  .addImport(MockBean.class)
                  .addImport("com.alan.generator.junit.testcase.base.BaseControllerTest")
                  .addAnnotation("WebMvcTest(controllers = " + entityName + ".class)")
                  .addExtend("BaseControllerTest");

  	objectStructure.addAttribute("MockMvc", "mockMvc", Autowired.class);
  	
  	// Add @MockBean on dependency
	Class<?> entityClazz = loadClass(entityClass);
	for (Field field : entityClazz.getDeclaredFields()) {
	    objectStructure.addImport(field.getType());
	    objectStructure.addAttribute(field.getType().getSimpleName(), field.getName(), MockBean.class);
	}
  	
	
	// Build GIVEN clause
	objectStructure.addImport(Mockito.class);
	objectStructure.addImport(Account.class);
	
	StringBuilder sbGiven = new StringBuilder();
	sbGiven.append(BuildHelper.buildBodyAtNewLine("Account account = null; //TODO Test data"));
	sbGiven.append(BuildHelper.buildBodyAtNewLine("Mockito.when(accountService.findByName(Mockito.anyString())).thenReturn(account);"));
	this.givenClause = sbGiven.toString();

	
	// Build WHEN clause
	objectStructure.addImport(RequestBuilder.class);
	objectStructure.addImport(MockMvcRequestBuilders.class);
	objectStructure.addImport(MediaType.class);
	
	StringBuilder sbWhen = new StringBuilder();
	sbWhen.append(BuildHelper.buildBodyAtNewLine("RequestBuilder reqeust = MockMvcRequestBuilders.get(\"/account/findByName\")"));
	sbWhen.append(BuildHelper.buildBodyAtNewLine(".contentType(MediaType.APPLICATION_JSON)"));
	sbWhen.append(BuildHelper.buildBodyAtNewLine(".param(\"name\", \"alanhuang\");")); //TODO if-else
	this.whenClause = sbWhen.toString();

	
	// Build THEN clause
	objectStructure.addImport(MockMvcResultMatchers.class);
	
	StringBuilder sbThen = new StringBuilder();
	sbThen.append(BuildHelper.buildBodyAtNewLine("mockMvc.perform(reqeust).andExpect(MockMvcResultMatchers.status().isOk());"));
	this.thenClause = sbThen.toString();
	
	
	// Add test method
  	objectStructure.addMethod(createTestCase("given_when_then"));
  	
	if (additionalExtends != null) {
	    for (String additionalExtend : additionalExtends) {
		objectStructure.addImport(additionalExtend);
		objectStructure.addExtend(GeneratorUtil.getSimpleClassName(additionalExtend), entityName);
	    }
	}
	this.javaCodeBuilder = new JavaCodeBuilder(objectStructure);
    }
    
    private Class<?> loadClass(String className) {
	try {
	    return Class.forName(className);
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
	return null;
    }
    
    
    @Override
    protected ObjectMethod createTestCase(String methodName) {
	ObjectMethod objectMethod = new ObjectMethod(ScopeValues.PUBLIC, methodName);
	objectMethod.addThrows(Exception.class);
	objectMethod.addAnnotation("@Test");

	StringBuilder sb = new StringBuilder();
	sb.append(BuildHelper.buildBodyAtNewLine("// GIVEN"));
	sb.append(getGivenClause());
	sb.append(BuildHelper.buildBodyAtNewLine(""));
	sb.append(BuildHelper.buildBodyAtNewLine("// WHEN"));
	sb.append(getWhenClause());
	sb.append(BuildHelper.buildBodyAtNewLine(""));
	sb.append(BuildHelper.buildBodyAtNewLine("// THEN"));
	sb.append(getThenClause());
	objectMethod.setBody(sb.toString());

	return objectMethod;
    }

    public String getGivenClause() {
        return givenClause;
    }

    public String getWhenClause() {
        return whenClause;
    }

    public String getThenClause() {
        return thenClause;
    }

}
