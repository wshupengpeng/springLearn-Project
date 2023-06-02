package com.spring.environment.listener;

import com.spring.environment.event.ApolloRefreshEvent;
import lombok.Data;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/6/2 16:01
 */
@Component
@Data
public class ApolloRefreshListener {

    private CompositePropertySource composite;

    //    public ApolloRefreshListener(CompositePropertySource composite) {
//        this.composite = composite;
//    }
    @EventListener
    public void onApplicationEvent(ApolloRefreshEvent apolloRefreshEvent) {
        // 获取propertySource
        PropertySource propertySource = (PropertySource) apolloRefreshEvent.getSource();

        // 更新
        String name = propertySource.getName();

        if (composite.containsProperty(name)) {
            Collection<PropertySource<?>> propertySources = composite.getPropertySources();
            Iterator<PropertySource<?>> iterator = propertySources.iterator();
            while(iterator.hasNext()){
                PropertySource<?> next = iterator.next();
                if(next.containsProperty(name)){
                    iterator.remove();
                    propertySources.add(propertySource);
                    break;
                }
            }
        }else{
            composite.addPropertySource(propertySource);
        }

    }

}
