package com.spring.resolver.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CurLogin {

}
