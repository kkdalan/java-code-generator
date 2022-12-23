package com.alan.generator.junit;

import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;

import com.alan.generator.common.AbstractTemplateSupportProvider;
import com.alan.generator.junit.structure.JUnitTestCaseStructure;
import com.cmeza.sdgenerator.util.CustomResourceLoader;
import com.cmeza.sdgenerator.util.Tuple;

public class JUnitTestCaseTemplateSupport extends AbstractTemplateSupportProvider {

    private CustomResourceLoader loader;
    private Set<String> additionalExtends;

    public JUnitTestCaseTemplateSupport(AnnotationAttributes attributes, Set<String> additionalExtends) {
	super(attributes);
	this.additionalExtends = additionalExtends;
    }

    public JUnitTestCaseTemplateSupport(CustomResourceLoader loader, Set<String> additionalExtends) {
	super(loader);
	this.loader = loader;
	this.additionalExtends = additionalExtends;
    }

    @Override
    protected Tuple<String, Integer> getContentFromTemplate(String testCasePackage, String simpleClassName,
	    String postfix, BeanDefinition beanDefinition, String additionalPackage) {
	return new JUnitTestCaseStructure(testCasePackage, simpleClassName, beanDefinition.getBeanClassName(),
		postfix, loader, additionalExtends).build();
    }

    @Override
    protected String getExcludeClasses() {
	return "excludeRepositoriesClasses";
    }

    @Override
    protected String getPostfix() {
	return "testCasePostfix";
    }

}