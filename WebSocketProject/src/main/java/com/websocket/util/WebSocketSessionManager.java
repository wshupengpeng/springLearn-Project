package com.websocket.util;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: websocket session管理工具类
 * @Author 01415355
 * @Date 2023/2/3 15:26
 */
public class WebSocketSessionManager {

    private static final Map<String, WebSocketSession> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     *  添加session
     * @param key
     * @param webSocketSession
     */
    public static void add(String key, WebSocketSession webSocketSession) {
        SESSION_POOL.put(key,webSocketSession);
    }


    /**
     * 删除 session,会返回删除的 session
     * @param key
     * @return
     */
    public static WebSocketSession remove(String key) {
        // 删除 session
        return SESSION_POOL.remove(key);
    }

    /**
     * 删除并同步关闭连接
     * @param key
     */
    public static void removeAndClose(String key) {
        WebSocketSession session = remove(key);
        if (session != null) {
            try {
                // 关闭连接
                session.close();
            } catch (IOException e) {
                // todo: 关闭出现异常处理
                e.printStackTrace();
            }
        }
    }
    /**
     * 获得 session
     * @param key
     * @return
     */
    public static WebSocketSession get(String key) {
        // 获得 session
        return SESSION_POOL.get(key);
    }

    /**
     *  获取所有会话
     * @return
     */
    public static Map<String,WebSocketSession> getSessionPool(){
        return SESSION_POOL;
    }

}
