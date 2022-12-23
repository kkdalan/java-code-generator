package com.alan.generator.junit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ComponentScan
@Import(JUnitGeneratorManager.class)
public @interface JUnitGenerator {

    String testCasePackage() default "";
    String testCasePostfix() default "TestCase";
    Class<?>[] excludeRepositoriesClasses() default {};

    String managerPackage() default "";
    String managerPostfix() default "Test";
    Class<?>[] excludeManagerClasses() default {};

    @AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
    String[] entityPackage() default {};

    boolean debug() default false;

    boolean onlyAnnotations() default false;

    boolean overwrite() default false;

    Class<?>[] additionalExtends() default {};
}
