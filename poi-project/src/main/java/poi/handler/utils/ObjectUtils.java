package poi.handler.utils;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/12 10:30
 */
public class ObjectUtils {


    public static <T> T getFirstNonNull(T ...elements){
        if(elements != null){
            for(T element : elements){
                if(element != null){
                    return element;
                }
            }
        }
        return null;
    }
}
