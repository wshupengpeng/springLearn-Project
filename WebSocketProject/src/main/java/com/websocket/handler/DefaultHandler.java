package com.websocket.handler;

import com.alibaba.fastjson.JSONObject;
import com.websocket.util.WebSocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import sun.security.krb5.internal.PAData;

import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/2/3 9:49
 */
@Component
@Slf4j
public class DefaultHandler implements WebSocketHandler {


    private static final Map<String, WebSocketSession> sessionPool = new ConcurrentHashMap<>();

    private static Long preReceiveTime = null;
    /**
     *  建立链接
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        String acceptedProtocol = webSocketSession.getAcceptedProtocol();
        Map<String, Object> attributes = webSocketSession.getAttributes();
        webSocketSession.getHandshakeHeaders().forEach((k,v)->{
            log.info("建立链接请求头key:{},value:{}", k, v);
        });
        int binaryMessageSizeLimit = webSocketSession.getBinaryMessageSizeLimit();
        List<WebSocketExtension> extensions = webSocketSession.getExtensions();
        HttpHeaders handshakeHeaders = webSocketSession.getHandshakeHeaders();
        InetSocketAddress localAddress = webSocketSession.getLocalAddress();
        Principal principal = webSocketSession.getPrincipal();
        log.info("acceptedProtocol:{},attributes:{},binaryMessageSizeLimit:{},extensions:{},handshakeHeaders:{},localAddress:{},principal{}",
                acceptedProtocol, attributes, binaryMessageSizeLimit, extensions, handshakeHeaders, localAddress, principal);
        WebSocketSessionManager.add("test",webSocketSession);
    }

    /**
     * 接收消息
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        if(preReceiveTime == null){
            preReceiveTime = System.currentTimeMillis();
        }
        if(webSocketMessage instanceof PongMessage){
            log.info("pong message");
            return;
        }

        log.info("socket receiveMessage time gap is :{}",System.currentTimeMillis() - preReceiveTime);
        Object payload = webSocketMessage.getPayload();
        if(payload.toString().equals("123")){
            throw new RuntimeException("测试异常");
        }
        webSocketSession.sendMessage(new TextMessage("发送消息"));
        String name = Thread.currentThread().getName();
        long id = Thread.currentThread().getId();
        log.info("webSocketMessage:{},payload:{},threadId:{},threadName:{}", webSocketMessage, JSONObject.toJSONString(payload), id, name);
        preReceiveTime = System.currentTimeMillis();
        log.info("attribute:{}",webSocketSession.getAttributes());
    }

    /**
     * 发生错误
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        log.info("当前处理发生异常。。。。。。");
    }

    /**
     * 链接关闭
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        log.info("afterConnectionClosed , closeStatus:{}", closeStatus);
    }


    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
