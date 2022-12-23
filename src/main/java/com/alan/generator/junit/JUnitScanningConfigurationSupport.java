package com.alan.generator.junit;


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.cmeza.sdgenerator.annotation.SDGenerate;
import com.cmeza.sdgenerator.annotation.SDNoGenerate;
import com.cmeza.sdgenerator.provider.ClassPathScanningProvider;

public class JUnitScanningConfigurationSupport {

    private final Environment environment;
    private final AnnotationAttributes attributes;
    private final AnnotationMetadata annotationMetadata;
    private final String[] entityPackage;
    private final boolean onlyAnnotations;

    public JUnitScanningConfigurationSupport(AnnotationMetadata annotationMetadata, AnnotationAttributes attributes, Environment environment){
        Assert.notNull(environment, "Environment must not be null!");
        Assert.notNull(environment, "AnnotationMetadata must not be null!");
        this.environment = environment;
        this.attributes = attributes;
        this.annotationMetadata = annotationMetadata;
        this.entityPackage = this.attributes.getStringArray("entityPackage");
        this.onlyAnnotations = this.attributes.getBoolean("onlyAnnotations");
    }

    public JUnitScanningConfigurationSupport(String[] entityPackage, boolean onlyAnnotations) {
        this.entityPackage = entityPackage;
        this.onlyAnnotations = onlyAnnotations;
        this.environment = null;
        this.annotationMetadata = null;
        this.attributes = null;
    }

    @SuppressWarnings("unchecked")
    public Iterable<String> getBasePackages() {

        if (entityPackage.length == 0) {
            String className = this.annotationMetadata.getClassName();
            return Collections.singleton(ClassUtils.getPackageName(className));
        } else {
            HashSet packages = new HashSet();
            packages.addAll(Arrays.asList(entityPackage));

            return packages;
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<BeanDefinition> getCandidates(ResourceLoader resourceLoader) {
        if(this.getBasePackages() == null){
            return Collections.emptyList();
        }

        ClassPathScanningProvider scanner = new ClassPathScanningProvider();
        scanner.setResourceLoader(resourceLoader);
        if (environment != null) {
            scanner.setEnvironment(this.environment);
        }

        scanner.setIncludeAnnotation(SDGenerate.class);
        scanner.setExcludeAnnotation(SDNoGenerate.class);
        if (!onlyAnnotations) {
            scanner.setIncludeAnnotation(Component.class);
        }

        Iterator filterPackages = this.getBasePackages().iterator();

        HashSet candidates = new HashSet();

        while(filterPackages.hasNext()) {
            String basePackage = (String)filterPackages.next();
            Set candidate = scanner.findCandidateComponents(basePackage);
            candidates.addAll(candidate);
        }

        return candidates;
    }
}

