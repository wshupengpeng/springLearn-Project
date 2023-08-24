package com.mybatis.test.listener;

import com.alibaba.fastjson.JSONObject;
import com.mybatis.test.entity.OpeateResult;
import com.mybatis.test.event.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @creater hpp
 * @Date 2023/8/23-21:59
 * @description:
 */
@Component
@Slf4j
public class LogListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void consumerLog(LogEvent logEvent) {
        OpeateResult opeateResult = (OpeateResult) logEvent.getSource();
        log.info("consumer log :{}", JSONObject.toJSONString(opeateResult));
    }


}
