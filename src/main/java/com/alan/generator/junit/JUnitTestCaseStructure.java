package com.alan.generator.junit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import com.alan.generator.common.support.JavaCodeBuilder;
import com.cmeza.sdgenerator.support.maker.builder.ObjectStructure;
import com.cmeza.sdgenerator.support.maker.builder.ObjectStructure.ObjectMethod;
import com.cmeza.sdgenerator.support.maker.values.CommonValues;
import com.cmeza.sdgenerator.support.maker.values.ObjectTypeValues;
import com.cmeza.sdgenerator.support.maker.values.ScopeValues;
import com.cmeza.sdgenerator.util.CustomResourceLoader;
import com.cmeza.sdgenerator.util.GeneratorException;
import com.cmeza.sdgenerator.util.GeneratorUtils;
import com.cmeza.sdgenerator.util.SDLogger;
import com.cmeza.sdgenerator.util.Tuple;


public class JUnitTestCaseStructure {

    private CustomResourceLoader loader;
    private JavaCodeBuilder javaCodeBuilder;
    private Integer error = 0;
    private final static Map<Class<?>, Class<?>> mapConvert = new HashMap<>();
    static {
        mapConvert.put(boolean.class, Boolean.class);
        mapConvert.put(byte.class, Byte.class);
        mapConvert.put(short.class, Short.class);
        mapConvert.put(char.class, Character.class);
        mapConvert.put(int.class, Integer.class);
        mapConvert.put(long.class, Long.class);
        mapConvert.put(float.class, Float.class);
        mapConvert.put(double.class, Double.class);
    }

    public JUnitTestCaseStructure(String testCasePackage, String entityName, String entityClass, String postfix, CustomResourceLoader loader, Set<String> additionalExtends) {
        this.loader = loader;
        String testCaseName = entityName + postfix;
//        Tuple<String, Boolean> entityId = getEntityId(entityClass);
//        if(entityId != null) {

            ObjectStructure objectStructure = new ObjectStructure(testCasePackage, ScopeValues.PUBLIC, ObjectTypeValues.CLASS, testCaseName)
                    .addImport(entityClass)
                    .addImport("org.junit.jupiter.api.AfterEach")
                    .addImport("org.junit.jupiter.api.BeforeEach")
                    .addImport("org.junit.jupiter.api.Test")
                    .addMethod(createSetupMethod())
            	    .addMethod(createTearDownMethod())
  		    .addMethod(createTestCaseMethod("given_when_then"));
//                    .addAnnotation("Repository")
//                    .addExtend("JpaRepository", entityName, GeneratorUtils.getSimpleClassName(entityId.left()))
//                    .addExtend("JpaSpecificationExecutor", entityName);

            if (additionalExtends != null) {
                for(String additionalExtend : additionalExtends) {
                    objectStructure.addImport(additionalExtend);
                    objectStructure.addExtend(GeneratorUtils.getSimpleClassName(additionalExtend), entityName);
                }
            }
            this.javaCodeBuilder = new JavaCodeBuilder(objectStructure);
//        }
    }
    
    private ObjectMethod createSetupMethod() {
   	ObjectMethod objectMethod = new ObjectMethod(ScopeValues.PUBLIC, "setUp");
   	objectMethod.addAnnotation("@BeforeEach");
   	StringBuilder sb = new StringBuilder();
   	sb.append(buildBodyAtNewLine("// TODO"));
   	objectMethod.setBody(sb.toString());
	return objectMethod;
    }
    
    private ObjectMethod createTearDownMethod() {
   	ObjectMethod objectMethod = new ObjectMethod(ScopeValues.PUBLIC, "tearDown");
   	objectMethod.addAnnotation("@AfterEach");
   	StringBuilder sb = new StringBuilder();
   	sb.append(buildBodyAtNewLine("// TODO"));
   	objectMethod.setBody(sb.toString());
	return objectMethod;
    }
    
    private ObjectMethod createTestCaseMethod(String methodName) {
	ObjectMethod objectMethod = new ObjectMethod(ScopeValues.PUBLIC, methodName);
	objectMethod.addAnnotation("@Test");

	StringBuilder sb = new StringBuilder();
	sb.append(buildBodyAtNewLine("// GIVEN"));
	sb.append(buildBodyAtNewLine("// TODO"));
	sb.append(buildBodyAtNewLine(""));
	sb.append(buildBodyAtNewLine("// WHEN"));
	sb.append(buildBodyAtNewLine("// TODO"));
	sb.append(buildBodyAtNewLine(""));
	sb.append(buildBodyAtNewLine("// THEN"));
	sb.append(buildBodyAtNewLine("// TODO"));
	objectMethod.setBody(sb.toString());
	
	return objectMethod;
    }
    
    public static String buildBodyAtNewLine(String bodyLine) {
        return CommonValues.TAB.getValue() + bodyLine + CommonValues.NEWLINE.getValue();
    }
    
    @SuppressWarnings("unchecked")
    private Tuple<String, Boolean> getEntityId(String entityClass){
        try {
            Class<?> entity;
            if (loader == null) {
                entity = Class.forName(entityClass);
            } else {
                entity = loader.getUrlClassLoader().loadClass(entityClass);
            }

            while (entity != null){
                for (Field field : entity.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(EmbeddedId.class)) {
                        Class<?> dataType = field.getType();
                        if (field.getType().isPrimitive()) {
                            dataType = this.primitiveToObject(field.getType());
                        }
                        return new Tuple<>(dataType.getName(), this.isCustomType(dataType));
                    }
                }

                for (Method method : entity.getDeclaredMethods()) {
                    if (!method.getReturnType().equals(Void.TYPE) && (method.isAnnotationPresent(Id.class) || method.isAnnotationPresent(EmbeddedId.class))) {
                        Class<?> dataType = method.getReturnType();
                        if (method.getReturnType().isPrimitive()) {
                            dataType = this.primitiveToObject(method.getReturnType());
                        }
                        return new Tuple<>(dataType.getName(), this.isCustomType(dataType));
                    }
                }
                entity = entity.getSuperclass();
            }

            error = SDLogger.addError("Repository Error: Primary key not found in " + GeneratorUtils.getSimpleClassName(entityClass) + ".java");
            return null;
        } catch (GeneratorException ex) {
            error = SDLogger.addError(ex.getMessage());
            return null;
        } catch (Exception e) {
            error = SDLogger.addError("Repository Error: Failed to access entity " + GeneratorUtils.getSimpleClassName(entityClass) + ".java");
            return null;
        }
    }

    public Tuple<String, Integer> build(){
        return new Tuple<>(javaCodeBuilder == null ? null : javaCodeBuilder.build(), error);
    }

    private boolean isCustomType(Class<?> clazz) {
        return  !clazz.isAssignableFrom(Boolean.class) &&
                !clazz.isAssignableFrom(Byte.class) &&
                !clazz.isAssignableFrom(String.class) &&
                !clazz.isAssignableFrom(Integer.class) &&
                !clazz.isAssignableFrom(Long.class) &&
                !clazz.isAssignableFrom(Float.class) &&
                !clazz.isAssignableFrom(Double.class);
    }

    private Class<?> primitiveToObject(Class<?> clazz) {
        Class<?> convertResult = mapConvert.get(clazz);
        if (convertResult == null) {
            throw new GeneratorException("Type parameter '" + clazz.getName() + "' is incorrect");
        }
        return convertResult;
    }

}
