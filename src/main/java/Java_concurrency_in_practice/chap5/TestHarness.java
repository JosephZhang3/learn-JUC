package Java_concurrency_in_practice.chap5;

import java.util.concurrent.*;

/**
 * 闭锁（倒读数门闩 count down latch）的应用
 *
 * @author jianghao.zhang
 */
public class TestHarness {

    /**
     * 多线程任务计时
     *
     * @param nThreads 线程数
     * @param task     线程任务
     * @return 执行耗时秒数
     * @throws InterruptedException 中断异常
     */
    public double timeTasks(int nThreads, Runnable task) throws InterruptedException {
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(() -> {
                try {
                    startGate.await();// 启动门等待计数器达到0，发令枪响
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    endGate.countDown();// 表示有一个事件已经发生了，结束门计数减1
                    // 有 nThreads 个事件发生，结束门计数减 1 * nThreads 次，结束门计数减到0时，nThreads个线程全部都执行完
                }
            });
            t.start();
        }

        long start = System.nanoTime();
        startGate.countDown();// 把启动门的计数设置为0，使得各工作线程的run方法中的 await() 结束等待，相当于发令枪声
        endGate.await();// 结束门 countDown 执行 nThreads 次之后，结束门计数减到0，此时await结束等待
        long end = System.nanoTime();
        return (end - start) / 1000000000.0;
    }

    public static void main(String[] args) throws InterruptedException {
        // 定义 Runnable task
        Runnable r = () -> {
            System.out.println("睡2秒");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        try {
            double time = new TestHarness().timeTasks(20, r);
            System.out.println("总耗时" + time + "秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
