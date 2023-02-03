package com.websocket.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/2/3 9:52
 */
@Component
@Slf4j
public class DefaultInterceptor implements HandshakeInterceptor {

    /**
     *  webSocket 在握手阶段进行的一些校验
     *  具体参数实际运行时看看
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        log.info("--------- webSocket handshake begin  -----------");
        log.info("--------- remote address is :{} -------",serverHttpRequest.getRemoteAddress());
        // 1 获取用户当前token

        HttpHeaders headers = serverHttpRequest.getHeaders();
        log.info("webSocket beforeHandshake begin,map:{}",map);
        return true;
    }

    /**
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        log.info("webSocket afterHandshake begin");
    }
}
