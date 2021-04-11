package Java_concurrency_in_practice.other;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolTest {
    public static void main(String[] args) {
        System.out.println("count of cores : " + Runtime.getRuntime().availableProcessors());
        int c = Runtime.getRuntime().availableProcessors();
        // 8C32G机器上线程数17
        ExecutorService es = Executors.newFixedThreadPool(2 * c + 1);

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            es.execute(() -> System.out.println(Thread.currentThread().getName() + "-第" + (finalI + 1) + "个任务"));
        }

        es.shutdown();
    }
}
