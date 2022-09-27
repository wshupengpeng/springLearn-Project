package com.spring.test;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletablefutureTest {
    private ThreadPoolExecutor executor;
    private static final Integer CORE_SIZE = Runtime.getRuntime().availableProcessors();

    @Before
    public void initThreadPool() {
        executor = new ThreadPoolExecutor(CORE_SIZE, 2 * CORE_SIZE + 1,
                60L, TimeUnit.SECONDS,new LinkedBlockingQueue<>(),new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 测试 Completablefuture 部分任务成功部分失败看最终结果
     *todo 测试目的，Completablefuture是异步线程，为啥一个线程报错会透传到主线程，需要追一下原理。
     */
    @Test
    public void testTaskFailed() {
        List<String> result = Arrays.asList(1, 2, 3, 4, 5).stream().map(e -> CompletableFuture.supplyAsync(() -> {
            if (e == 5) {
                throw new RuntimeException("异常测试");
            }
            return "threadpool execute ：" + e;
        }, executor)).map(r->r.join()).collect(Collectors.toList());
        System.out.println(result);
    }
}
