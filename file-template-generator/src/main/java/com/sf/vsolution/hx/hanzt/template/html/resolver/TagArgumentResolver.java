package com.sf.vsolution.hx.hanzt.template.html.resolver;

import com.sf.vsolution.hx.hanzt.template.html.param.RichText;

/**
 * @Description: 参数解析器, 用于公共标签参数解析，比如style参数等
 * @Author 01415355
 * @Date 2023/4/12 10:52
 */
public interface TagArgumentResolver {

    void resolveArgument(RichText richText);

}
