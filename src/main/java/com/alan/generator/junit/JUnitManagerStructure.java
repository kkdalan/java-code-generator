package com.alan.generator.junit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alan.generator.common.support.JavaCodeBuilder;
import com.alan.generator.common.util.GeneratorUtil;
import com.cmeza.sdgenerator.support.maker.builder.ObjectStructure;
import com.cmeza.sdgenerator.support.maker.values.ExpressionValues;
import com.cmeza.sdgenerator.support.maker.values.ObjectTypeValues;
import com.cmeza.sdgenerator.support.maker.values.ObjectValues;
import com.cmeza.sdgenerator.support.maker.values.ScopeValues;
import com.cmeza.sdgenerator.util.Tuple;

public class JUnitManagerStructure {

    private JavaCodeBuilder javaCodeBuilder;

    public JUnitManagerStructure(String managerPackage, String entityName, String entityClass, String postfix, String testCasePackage, String testCasePostfix, String additionalPackage) {

        String managerName = entityName + postfix;
        String testCaseName = entityName + testCasePostfix;
        String testCaseNameAttribute = GeneratorUtil.decapitalize(testCaseName);

        this.javaCodeBuilder = new JavaCodeBuilder(new ObjectStructure(managerPackage, ScopeValues.PUBLIC, ObjectTypeValues.CLASS, managerName)
                .addImport(testCasePackage + "." + (additionalPackage.isEmpty() ? "" : (additionalPackage + ".")) + testCaseName)
                .addImport(entityClass)
                .addImport(Autowired.class)
                .addImport(Component.class)
                .addAnnotation(Component.class)
                .addAttribute(testCaseName, testCaseNameAttribute)
                .addConstructor(new ObjectStructure.ObjectConstructor(ScopeValues.PUBLIC, managerName)
                        .addAnnotation(Autowired.class)
                        .addArgument(testCaseName, testCaseNameAttribute)
                        .addBodyLine(ObjectValues.THIS.getValue() + testCaseNameAttribute + ExpressionValues.EQUAL.getValue() + testCaseNameAttribute)
                )
        ).setAttributeBottom(false);

    }

    public Tuple<String, Integer> build(){
        return new Tuple<>(javaCodeBuilder == null ? null : javaCodeBuilder.build(), 0);
    }
}
