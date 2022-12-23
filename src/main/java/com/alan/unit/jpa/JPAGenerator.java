package com.alan.unit.jpa;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EntityScan
@Import(JPAGeneratorManager.class)
public @interface JPAGenerator {

    String repositoryPackage() default "";
    String repositoryPostfix() default "Repository";
    Class<?>[] excludeRepositoriesClasses() default {};

    String managerPackage() default "";
    String managerPostfix() default "Manager";
    Class<?>[] excludeManagerClasses() default {};

    @AliasFor(annotation = EntityScan.class, attribute = "basePackages")
    String[] entityPackage() default {};

    boolean debug() default false;

    boolean onlyAnnotations() default false;

    boolean overwrite() default false;

    Class<?>[] additionalExtends() default {};
}
