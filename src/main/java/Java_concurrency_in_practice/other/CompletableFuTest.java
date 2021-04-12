package Java_concurrency_in_practice.other;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * 测试使用 CompletableFuture
 *
 * @author jianghao.zhang
 */
public class CompletableFuTest {
    public static void main(String[] args) {
        multi(3,45,67,889,54);

        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });
        CompletableFuture<String> future = completableFuture
                .thenApplyAsync(s -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s + " World";
                });

//        try {
        // get方法是阻塞等待，会让主线程停止，弊端很大
//            System.out.println(future.get());
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }

        // thenAccept作用相当于注册一个执行成功时的回调函数，非阻塞形式，推荐使用
        future.thenAcceptAsync(result -> System.out.println("result is " + result));

        System.out.println("Have set callback function thenAcceptAsync.");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("JRE now shutdown")));

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void multi(int... ints){
        for (int i:ints             ) {
            System.out.println("now is "+i);
        }
    }
}
