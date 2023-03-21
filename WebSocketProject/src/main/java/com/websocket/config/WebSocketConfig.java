package com.websocket.config;

import com.websocket.handler.DefaultHandler;
import com.websocket.interceptor.DefaultInterceptor;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/2/3 9:44
 */
@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private DefaultHandler defaultHandler;

    @Autowired
    private DefaultInterceptor defaultInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        // 注册webSocket
        // 处理uri为/ws的请求
        webSocketHandlerRegistry.addHandler(defaultHandler,"/ws")
                // 设置拦截器
                .addInterceptors(defaultInterceptor)
                // 跨域设置
                .setAllowedOrigins("*");
//                .withSockJS();
    }



    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        container.setMaxSessionIdleTimeout(60000l);
        container.setAsyncSendTimeout(60000l);
        return container;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolScheduler = new ThreadPoolTaskScheduler();
        threadPoolScheduler.setThreadNamePrefix("SockJS-");
        threadPoolScheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        threadPoolScheduler.setRemoveOnCancelPolicy(true);
        return threadPoolScheduler;
    }
}
