package com.alan.unit.jpa;

import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;

import com.alan.unit.common.AbstractTemplateSupportProvider;
import com.cmeza.sdgenerator.util.CustomResourceLoader;
import com.cmeza.sdgenerator.util.Tuple;

public class JPARepositoryTemplateSupport extends AbstractTemplateSupportProvider {

    private CustomResourceLoader loader;
    private Set<String> additionalExtends;

    public JPARepositoryTemplateSupport(AnnotationAttributes attributes, Set<String> additionalExtends) {
	super(attributes);
	this.additionalExtends = additionalExtends;
    }

    public JPARepositoryTemplateSupport(CustomResourceLoader loader, Set<String> additionalExtends) {
	super(loader);
	this.loader = loader;
	this.additionalExtends = additionalExtends;
    }

    @Override
    protected Tuple<String, Integer> getContentFromTemplate(String repositoryPackage, String simpleClassName,
	    String postfix, BeanDefinition beanDefinition, String additionalPackage) {
	return new JPARepositoryStructure(repositoryPackage, simpleClassName, beanDefinition.getBeanClassName(),
		postfix, loader, additionalExtends).build();
    }

    @Override
    protected String getExcludeClasses() {
	return "excludeRepositoriesClasses";
    }

    @Override
    protected String getPostfix() {
	return "repositoryPostfix";
    }

}