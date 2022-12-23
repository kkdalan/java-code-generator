package com.alan.generator.junit.structure;

import java.lang.reflect.Field;
import java.util.Set;

import com.alan.generator.common.support.BuildHelper;
import com.alan.generator.common.support.JavaCodeBuilder;
import com.alan.generator.common.support.ObjectStructure;
import com.alan.generator.common.support.ObjectStructure.ObjectMethod;
import com.alan.generator.common.util.GeneratorUtil;
import com.cmeza.sdgenerator.support.maker.values.ObjectTypeValues;
import com.cmeza.sdgenerator.support.maker.values.ScopeValues;
import com.cmeza.sdgenerator.util.CustomResourceLoader;

public class ControllerTestCaseStructure extends JUnitTestCaseStructure {

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
                  .addImport("org.springframework.beans.factory.annotation.Autowired")
                  .addImport("com.alan.generator.junit.testcase.base.BaseControllerTest")
                  .addAnnotation("WebMvcTest(controllers = " + entityName + ".class)")
                  .addExtend("BaseControllerTest");

  	objectStructure.addAttribute("MockMvc", "mockMvc");
  	
	Class<?> entityClazz = loadClass(entityClass);
	for (Field field : entityClazz.getDeclaredFields()) {
	    objectStructure.addImport(field.getType());
	    objectStructure.addAttribute(field.getType().getSimpleName(), field.getName());
	}
  	
//  	objectStructure.addConstructor(new ObjectStructure.ObjectConstructor(ScopeValues.PUBLIC, testCaseName)
//                     .addAnnotation(Autowired.class)
//                     .addArgument(testCaseName, testCaseNameAttribute)
//                     .addBodyLine(ObjectValues.THIS.getValue() + testCaseNameAttribute + ExpressionValues.EQUAL.getValue() + testCaseNameAttribute)
  	
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
	objectMethod.addAnnotation("@Test");

	StringBuilder sb = new StringBuilder();
	sb.append(BuildHelper.buildBodyAtNewLine("// GIVEN"));
	sb.append(BuildHelper.buildBodyAtNewLine("// TODO"));
	sb.append(BuildHelper.buildBodyAtNewLine(""));
	sb.append(BuildHelper.buildBodyAtNewLine("// WHEN"));
	sb.append(BuildHelper.buildBodyAtNewLine("// TODO"));
	sb.append(BuildHelper.buildBodyAtNewLine(""));
	sb.append(BuildHelper.buildBodyAtNewLine("// THEN"));
	sb.append(BuildHelper.buildBodyAtNewLine("// TODO"));
	objectMethod.setBody(sb.toString());

	return objectMethod;
    }

}
