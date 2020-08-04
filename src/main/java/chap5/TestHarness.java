package chap5;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁的应用
 *
 * @author jianghao.zhang
 */
public class TestHarness {

    /**
     * 多线程任务计时
     *
     * @param nThreads 线程数
     * @param task     线程任务
     * @return 执行耗时
     * @throws InterruptedException 中断异常
     */
    public long timeTasks(int nThreads, Runnable task) throws InterruptedException {
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(() -> {
                try {
                    startGate.await();// 启动门等待计数器达到0
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    endGate.countDown();// 表示有一个事件已经发生了，结束门计数减1
                }
            });
            t.start();
        }

        long start = System.nanoTime();
        startGate.countDown();// 把启动门的计数设置为0，启动门关闭
        endGate.await();// 结束门等待计数器达到0
        long end = System.nanoTime();
        return end - start;
    }

    public static void main(String[] args) {
        Runnable r = () -> {
            System.out.println("睡2秒");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        try {
            long time = new TestHarness().timeTasks(4, r);
            System.out.println("总耗时" + time / 1000000000.0 + "秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
