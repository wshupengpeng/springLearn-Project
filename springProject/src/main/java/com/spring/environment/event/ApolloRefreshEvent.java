package com.spring.environment.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.env.PropertySource;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/6/2 16:06
 */
public class ApolloRefreshEvent extends ApplicationEvent {

    public ApolloRefreshEvent(PropertySource propertySource) {
        super(propertySource);
    }

}
