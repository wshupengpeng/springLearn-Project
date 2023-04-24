package poi;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/23 16:39
 */
public class TestDemo {

    @Test
    public void threadPoolTest() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,2,2L, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
        for(int i = 0; i < 10; i++){
            threadPoolExecutor.execute(()->{
                System.out.println(Thread.currentThread().getName());
            });
        }
        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(10L,TimeUnit.SECONDS);
        if(!threadPoolExecutor.isTerminated()){
            threadPoolExecutor.shutdownNow();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                5,5,1L, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1)
                ,new BasicThreadFactory.Builder().namingPattern("businessInformationVerification-%d").build());
//        for(int i = 0; i < 10; i++){
//            threadPoolExecutor.execute(()->{
//                System.out.println(Thread.currentThread().getName());
//            });
//        }
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
//        List<String> collect = list.stream().map((t) -> CompletableFuture.supplyAsync(() -> printThread(), threadPoolExecutor)).map(CompletableFuture::join).collect(Collectors.toList());
        list.stream().map((t) -> CompletableFuture.runAsync(() -> printThread(), threadPoolExecutor).join()).collect(Collectors.toList());
        System.out.println("shutdown");
        threadPoolExecutor.shutdown();
    }

    public static String printThread(){
        try {
            Thread.sleep(2000l);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
