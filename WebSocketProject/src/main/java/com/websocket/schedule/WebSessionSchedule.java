package com.websocket.schedule;

import com.websocket.util.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: websocket 会话定时任务
 * @Author 01415355
 * @Date 2023/3/20 9:51
 */
@Component
@Slf4j
public class WebSessionSchedule {

    @Scheduled(cron = "0/1 * * * * ?")
    public void heartBeat(){
        Map<String, WebSocketSession> sessionPool = WebSocketSessionManager.getSessionPool();
        sessionPool.entrySet().parallelStream().forEach(entry -> {
            if(entry.getValue().isOpen()){
                InetSocketAddress remoteAddress = entry.getValue().getRemoteAddress();
                log.info("heart beat begin ,remote address:{}", remoteAddress);
                try {
                    entry.getValue().sendMessage(new TextMessage("ping"));
                } catch (IOException e) {
                    log.info("heart Beat failed, error:", e);
                }
            }else{
                WebSocketSessionManager.removeAndClose(entry.getKey());
            }

        });
        log.info("heart beat end");
    }

}
