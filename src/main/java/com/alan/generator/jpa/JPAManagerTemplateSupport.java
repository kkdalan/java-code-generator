package com.alan.generator.jpa;

import java.io.File;
import java.util.Arrays;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;

import com.alan.generator.common.AbstractTemplateSupportProvider;
import com.cmeza.sdgenerator.support.maker.ManagerStructure;
import com.cmeza.sdgenerator.util.CustomResourceLoader;
import com.cmeza.sdgenerator.util.GeneratorUtils;
import com.cmeza.sdgenerator.util.Tuple;

public class JPAManagerTemplateSupport extends AbstractTemplateSupportProvider {

    private String repositoryPackage;
    private String repositoryPostfix;

    public JPAManagerTemplateSupport(AnnotationAttributes attributes, String repositoryPackage, String repositoryPostfix) {
	super(attributes);
	this.repositoryPackage = repositoryPackage;
	this.repositoryPostfix = repositoryPostfix;
	this.findFilterRepositories();
    }

    public JPAManagerTemplateSupport(CustomResourceLoader customResourceLoader) {
	super(customResourceLoader);
	this.repositoryPackage = customResourceLoader.getRepositoryPackage();
	this.repositoryPostfix = customResourceLoader.getRepositoryPostfix();
	this.findFilterRepositories();
    }

    private void findFilterRepositories() {
	String repositoryPath = GeneratorUtils.getAbsolutePath() + repositoryPackage.replace(".", "/");
	File[] repositoryFiles = GeneratorUtils.getFileList(repositoryPath, repositoryPostfix);
	this.setIncludeFilter(Arrays.asList(repositoryFiles));
	this.setIncludeFilterPostfix(repositoryPostfix);
    }

    @Override
    protected Tuple<String, Integer> getContentFromTemplate(String mPackage, String simpleClassName, String postfix,
	    BeanDefinition beanDefinition, String additionalPackage) {
	return new ManagerStructure(mPackage, simpleClassName, beanDefinition.getBeanClassName(), postfix,
		repositoryPackage, repositoryPostfix, additionalPackage).build();
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
