package support;

import com.spring.excel.support.AnnotationDefinition;

public interface ExcelExecutor {

    void execute(AnnotationDefinition defintion);


    boolean support(AnnotationDefinition definition);

}
