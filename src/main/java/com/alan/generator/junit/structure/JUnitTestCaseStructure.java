package com.alan.generator.junit.structure;

import java.util.Set;

import com.alan.generator.common.support.BuildHelper;
import com.alan.generator.common.support.JavaCodeBuilder;
import com.alan.generator.common.util.GeneratorUtil;
import com.cmeza.sdgenerator.support.maker.builder.ObjectStructure;
import com.cmeza.sdgenerator.support.maker.builder.ObjectStructure.ObjectMethod;
import com.cmeza.sdgenerator.support.maker.values.ObjectTypeValues;
import com.cmeza.sdgenerator.support.maker.values.ScopeValues;
import com.cmeza.sdgenerator.util.CustomResourceLoader;
import com.cmeza.sdgenerator.util.Tuple;


public class JUnitTestCaseStructure {

    private CustomResourceLoader loader;
    private JavaCodeBuilder javaCodeBuilder;
    private Integer error = 0;

    public JUnitTestCaseStructure(String testCasePackage, String entityName, String entityClass, String postfix, CustomResourceLoader loader, Set<String> additionalExtends) {
	buildJavaCode(testCasePackage, entityName, entityClass, postfix, loader, additionalExtends);
    }
    
    protected void buildJavaCode(String testCasePackage, String entityName, String entityClass, String postfix, CustomResourceLoader loader, Set<String> additionalExtends) {
	this.loader = loader;
	String testCaseName = entityName + postfix;
	        
//        Tuple<String, Boolean> entityId = getEntityId(entityClass);
//        if(entityId != null) {

            ObjectStructure objectStructure = new ObjectStructure(testCasePackage, ScopeValues.PUBLIC, ObjectTypeValues.CLASS, testCaseName)
                    .addImport(entityClass)
                    .addImport("org.junit.jupiter.api.AfterEach")
                    .addImport("org.junit.jupiter.api.BeforeEach")
                    .addImport("org.junit.jupiter.api.Test")
                    .addMethod(createSetup())
            	    .addMethod(createTearDown())
  		    .addMethod(createTestCase("given_when_then"));
//                    .addAnnotation("Repository")
//                    .addExtend("JpaRepository", entityName, GeneratorUtil.getSimpleClassName(entityId.left()))
//                    .addExtend("JpaSpecificationExecutor", entityName);

            if (additionalExtends != null) {
                for(String additionalExtend : additionalExtends) {
                    objectStructure.addImport(additionalExtend);
                    objectStructure.addExtend(GeneratorUtil.getSimpleClassName(additionalExtend), entityName);
                }
            }
            this.javaCodeBuilder = new JavaCodeBuilder(objectStructure);
//        }
    }
    
    protected ObjectMethod createSetup() {
   	ObjectMethod objectMethod = new ObjectMethod(ScopeValues.PUBLIC, "setUp");
   	objectMethod.addAnnotation("@BeforeEach");
   	StringBuilder sb = new StringBuilder();
   	sb.append(BuildHelper.buildBodyAtNewLine("// TODO"));
   	objectMethod.setBody(sb.toString());
	return objectMethod;
    }
    
    protected ObjectMethod createTearDown() {
   	ObjectMethod objectMethod = new ObjectMethod(ScopeValues.PUBLIC, "tearDown");
   	objectMethod.addAnnotation("@AfterEach");
   	StringBuilder sb = new StringBuilder();
   	sb.append(BuildHelper.buildBodyAtNewLine("// TODO"));
   	objectMethod.setBody(sb.toString());
	return objectMethod;
    }
    
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
    
    public Tuple<String, Integer> build(){
        return new Tuple<>(javaCodeBuilder == null ? null : javaCodeBuilder.build(), error);
    }
    
}
