package annotation;


import enums.PageEnum;

import java.lang.annotation.*;

/**
 *  用于标记分页参数
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportPageAnnotation {
    PageEnum subselection();

    long defaultValue();
}