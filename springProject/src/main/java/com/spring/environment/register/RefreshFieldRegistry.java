package com.spring.environment.register;

import com.spring.environment.param.RefreshField;
import org.apache.naming.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/6/2 16:45
 */
@Component
public class RefreshFieldRegistry {

    private Map<String, List<RefreshField>> refreshFieldMap = new HashMap<>();

    public void register(RefreshField refreshField){
        if(refreshFieldMap.containsKey(refreshField.getValue())){
            refreshFieldMap.get(refreshField.getValue()).add(refreshField);
        }else{
            List<RefreshField> refreshFieldList = new ArrayList<>();
            refreshFieldList.add(refreshField);
            refreshFieldMap.put(refreshField.getValue(),refreshFieldList);
        }
    }

    public Collection<RefreshField> get(String key){
        return refreshFieldMap.get(key);
    }

}
