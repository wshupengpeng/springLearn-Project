package com.java;

import org.junit.Test;

import java.util.*;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/5/19 18:09
 */
public class ThreadTest {

    @Test
    public void inheritable() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
//        ThreadLocal threadLocal = InheritableThreadLocal.withInitial(HashMap::new);
        InheritableThreadLocal threadLocal = new InheritableThreadLocal();
        threadLocal.set(Collections.singletonMap("tranceId", UUID.randomUUID().toString()));
//        ((Map) threadLocal.get()).put("tranceId",UUID.randomUUID().toString());

//        new Thread(()->{
//            System.out.println(Thread.currentThread().getName() + ":" + ((Map<?, ?>) threadLocal.get()).get("tranceId"));
//        }).start();
        list.parallelStream().forEach(item -> {
            System.out.println(Thread.currentThread().getName() + ":" + ((Map<?, ?>) threadLocal.get()).get("tranceId"));
        });
    }
}
