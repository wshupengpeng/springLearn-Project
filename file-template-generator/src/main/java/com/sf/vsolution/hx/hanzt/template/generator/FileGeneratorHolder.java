package com.sf.vsolution.hx.hanzt.template.generator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.sf.vsolution.hx.hanzt.template.generator.enums.GeneratorModeEnum;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateConfiguration;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplatePreResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/6/7 15:32
 */
public class FileGeneratorHolder {

    private static Map<GeneratorModeEnum, AbstractFileGenerator> fileGeneratorList = new HashMap<>();

    static {
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(ClassUtil.getPackage(AbstractFileGenerator.class), AbstractFileGenerator.class);
        if (CollUtil.isNotEmpty(classes)) {
            for (Class clazz : classes) {
                AbstractFileGenerator generator = (AbstractFileGenerator) ReflectUtil.newInstance(clazz);
                fileGeneratorList.put(generator.getGeneratorMode(), generator);
            }
        }
    }

    public static FileTemplatePreResult generateFile(FileTemplateConfiguration configuration) {
        AbstractFileGenerator generator = fileGeneratorList.get(configuration.getModeEnum());
        return generator.execute(configuration);
    }
}
