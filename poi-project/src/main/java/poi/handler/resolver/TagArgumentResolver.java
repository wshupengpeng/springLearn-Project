package poi.handler.resolver;

import org.jsoup.nodes.Node;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;

/**
 * @Description: 参数解析器,用于公共标签参数解析，比如style参数等
 * @Author 01415355
 * @Date 2023/4/12 10:52
 */
public abstract class TagArgumentResolver {

    public TagArgumentResolver() {
        AbstractHtmlTagHandler.resolverList.add(this);
    }

    public abstract boolean isSupport(Node currentNode);

    public abstract void resolveArgument(DocumentParam documentParam);

}
