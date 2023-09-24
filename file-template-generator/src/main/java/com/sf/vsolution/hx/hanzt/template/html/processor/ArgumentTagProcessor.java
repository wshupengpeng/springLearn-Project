package com.sf.vsolution.hx.hanzt.template.html.processor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import com.sf.vsolution.hx.hanzt.template.html.resolver.TagArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description: 标签参数处理器
 * @Author 01415355
 * @Date 2023/4/28 9:39
 */
@Slf4j
public class ArgumentTagProcessor implements HtmlTagProcessor{
    private static List<TagArgumentResolver> resolverList = new ArrayList<>();

    static {
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(ClassUtil.getPackage(TagArgumentResolver.class), TagArgumentResolver.class);
        if (CollUtil.isNotEmpty(classes)) {
            for (Class clazz:classes) {
                TagArgumentResolver resolver = (TagArgumentResolver) ReflectUtil.newInstance(clazz);
                resolverList.add(resolver);
            }
        }
    }
    @Override
    public void preHandle(RichText richText) {
        if(CollectionUtils.isEmpty(resolverList)){
            log.info("resovler is Empty,continue argument resolve");
            return;
        }
        for (TagArgumentResolver resolver : resolverList) {
            resolver.resolveArgument(richText);
        }
    }

}
