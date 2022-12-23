package com.alan.generator.junit;

import java.io.File;
import java.util.Arrays;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;

import com.alan.generator.common.AbstractTemplateSupportProvider;
import com.alan.generator.common.util.GeneratorUtil;
import com.alan.generator.junit.structure.JUnitManagerStructure;
import com.cmeza.sdgenerator.util.CustomResourceLoader;
import com.cmeza.sdgenerator.util.Tuple;

public class JUnitManagerTemplateSupport extends AbstractTemplateSupportProvider {

    private String testCasePackage;
    private String testCasePostfix;

    public JUnitManagerTemplateSupport(AnnotationAttributes attributes, String testCasePackage, String testCasePostfix) {
	super(attributes);
	this.testCasePackage = testCasePackage;
	this.testCasePostfix = testCasePostfix;
	this.findFilterRepositories();
    }

    public JUnitManagerTemplateSupport(CustomResourceLoader customResourceLoader) {
	super(customResourceLoader);
	this.testCasePackage = customResourceLoader.getRepositoryPackage();
	this.testCasePostfix = customResourceLoader.getRepositoryPostfix();
	this.findFilterRepositories();
    }

    private void findFilterRepositories() {
	String testCasePath = GeneratorUtil.getMainFolderAbsolutePath() + testCasePackage.replace(".", "/");
	File[] testCaseFiles = GeneratorUtil.getFileList(testCasePath, testCasePostfix);
	this.setIncludeFilter(Arrays.asList(testCaseFiles));
	this.setIncludeFilterPostfix(testCasePostfix);
    }

    @Override
    protected Tuple<String, Integer> getContentFromTemplate(String mPackage, String simpleClassName, String postfix,
	    BeanDefinition beanDefinition, String additionalPackage) {
	return new JUnitManagerStructure(mPackage, simpleClassName, beanDefinition.getBeanClassName(), postfix,
		testCasePackage, testCasePostfix, additionalPackage).build();
    }

    @Override
    protected String getExcludeClasses() {
	return "excludeManagerClasses";
    }

    @Override
    protected String getPostfix() {
	return "managerPostfix";
    }

}
