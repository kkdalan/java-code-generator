package com.alan.generator.junit;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import com.alan.generator.common.util.GeneratorUtil;
import com.cmeza.sdgenerator.util.SDLogger;
import com.google.common.collect.Iterables;

public class JUnitGeneratorManager implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware {

    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Assert.notNull(annotationMetadata, "AnnotationMetadata must not be null!");
        Assert.notNull(beanDefinitionRegistry, "BeanDefinitionRegistry must not be null!");

        if(annotationMetadata.getAnnotationAttributes(JUnitGenerator.class.getName()) != null) {

            AnnotationAttributes attributes = new AnnotationAttributes(annotationMetadata.getAnnotationAttributes(JUnitGenerator.class.getName()));

            String testCasePackage = attributes.getString("testCasePackage");
            String managerPackage = attributes.getString("managerPackage");

            if (!managerPackage.isEmpty() && testCasePackage.isEmpty()) {
                SDLogger.error("Repositories must be generated before generating managers");
                return;
            }

            if (!testCasePackage.isEmpty() || !managerPackage.isEmpty()){
                JUnitScanningConfigurationSupport configurationSource = new JUnitScanningConfigurationSupport(annotationMetadata, attributes, this.environment);

                Collection<BeanDefinition> candidates = configurationSource.getCandidates(resourceLoader);

//                String absolutePath = GeneratorUtil.getMainFolderAbsolutePath();
                String absolutePath = GeneratorUtil.getTestFolderAbsolutePath();
                if (absolutePath == null) {
                    SDLogger.addError("Could not define the absolute path!");
                    return;
                }

                if (!testCasePackage.isEmpty()){

                    String testCasesPath = absolutePath + testCasePackage.replace(".", "/");
                    Set<String> additionalExtends = this.validateExtends(attributes.getClassArray("additionalExtends"));
                    if (additionalExtends != null) {
                        JUnitTestCaseTemplateSupport testCaseTemplateSupport = new JUnitTestCaseTemplateSupport(attributes, additionalExtends);
                        testCaseTemplateSupport.initializeCreation(testCasesPath, testCasePackage, candidates, Iterables.toArray(configurationSource.getBasePackages(), String.class));
                    }
                }

                if (!testCasePackage.isEmpty() && !managerPackage.isEmpty()) {

                    String managerPath = absolutePath + managerPackage.replace(".", "/");

                    String testCasePostfix = attributes.getString("testCasePostfix");

                    JUnitManagerTemplateSupport managerTemplateSupport = new JUnitManagerTemplateSupport(attributes, testCasePackage, testCasePostfix);
                    managerTemplateSupport.initializeCreation(managerPath, managerPackage, candidates, Iterables.toArray(configurationSource.getBasePackages(), String.class));
                }

                SDLogger.printGeneratedTables(attributes.getBoolean("debug"));
            }

        }
    }

    private Set<String> validateExtends(Class<?>[] additionalExtends){
        Class<?> extendTemporal;
        boolean errorValidate = Boolean.FALSE;
        Set<String> additionalExtendsList = new LinkedHashSet<>();
        for (int i = 0; i < additionalExtends.length; i++) {
            extendTemporal = additionalExtends[i];
            SDLogger.addAdditionalExtend(extendTemporal.getName());

            if (!extendTemporal.isInterface()) {
                SDLogger.addError( String.format("'%s' is not a interface!", extendTemporal.getName()));
                errorValidate = Boolean.TRUE;
            } else {
                additionalExtendsList.add(extendTemporal.getName());
            }
        }

        if (errorValidate) {
            return null;
        }

        return additionalExtendsList;
    }

}
