package com.mybatis.test.event;

import com.mybatis.test.entity.OpeateResult;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @creater hpp
 * @Date 2023/8/23-21:59
 * @description:
 */
public class LogEvent extends ApplicationEvent {

    public LogEvent(OpeateResult opeateResult) {
        super(opeateResult);
    }



}
