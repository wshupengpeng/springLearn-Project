package annotation;

import com.spring.excel.enums.ExportModeEnum;
import com.spring.excel.processor.AbstractExcelPostProcessor;
import com.spring.excel.processor.NormalExcelPostProcessor;

import java.lang.annotation.*;

/**
 *  ExportExcel模块是为了设计一个通用化的导出方式，
 *  用于适配多场景下的导出情况
 */
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExportExcel {
    /**
     *  需要导出实体类
     * @return
     */
    Class<?> beanClass();

    String fileName();

    String sheetName() default "sheet";

    ExportModeEnum mode() default ExportModeEnum.NORMAL;

    Class<? extends AbstractExcelPostProcessor>[] postProcessor() default NormalExcelPostProcessor.class;

    long limit();
}
