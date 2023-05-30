package com.spring.environment.config;

import org.springframework.context.ApplicationEvent;

/**
 * @creater hpp
 * @Date 2023/5/30-2:14
 * @description:
 */
public class RefreshEvent extends ApplicationEvent {

    private String key;

    public String getKey() {
        return key;
    }

    public RefreshEvent(Object source, String key) {
        super(source);
        this.key = key;
    }
}