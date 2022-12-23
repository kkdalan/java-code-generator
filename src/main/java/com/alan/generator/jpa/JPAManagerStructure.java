package com.alan.generator.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alan.generator.common.support.JavaCodeBuilder;
import com.cmeza.sdgenerator.support.maker.builder.ObjectBuilder;
import com.cmeza.sdgenerator.support.maker.builder.ObjectStructure;
import com.cmeza.sdgenerator.support.maker.values.ExpressionValues;
import com.cmeza.sdgenerator.support.maker.values.ObjectTypeValues;
import com.cmeza.sdgenerator.support.maker.values.ObjectValues;
import com.cmeza.sdgenerator.support.maker.values.ScopeValues;
import com.cmeza.sdgenerator.util.GeneratorUtils;
import com.cmeza.sdgenerator.util.Tuple;

public class JPAManagerStructure {

    private JavaCodeBuilder javaCodeBuilder;

    public JPAManagerStructure(String managerPackage, String entityName, String entityClass, String postfix, String repositoryPackage, String repositoryPostfix, String additionalPackage) {

        String managerName = entityName + postfix;
        String repositoryName = entityName + repositoryPostfix;
        String repositoryNameAttribute = GeneratorUtils.decapitalize(repositoryName);

        this.javaCodeBuilder = new JavaCodeBuilder(new ObjectStructure(managerPackage, ScopeValues.PUBLIC, ObjectTypeValues.CLASS, managerName)
                .addImport(repositoryPackage + "." + (additionalPackage.isEmpty() ? "" : (additionalPackage + ".")) + repositoryName)
                .addImport(entityClass)
                .addImport(Autowired.class)
                .addImport(Component.class)
                .addAnnotation(Component.class)
                .addAttribute(repositoryName, repositoryNameAttribute)
                .addConstructor(new ObjectStructure.ObjectConstructor(ScopeValues.PUBLIC, managerName)
                        .addAnnotation(Autowired.class)
                        .addArgument(repositoryName, repositoryNameAttribute)
                        .addBodyLine(ObjectValues.THIS.getValue() + repositoryNameAttribute + ExpressionValues.EQUAL.getValue() + repositoryNameAttribute)
                )
        ).setAttributeBottom(false);

    }

    public Tuple<String, Integer> build(){
        return new Tuple<>(javaCodeBuilder == null ? null : javaCodeBuilder.build(), 0);
    }
}
