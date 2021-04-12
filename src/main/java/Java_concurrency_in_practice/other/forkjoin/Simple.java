package Java_concurrency_in_practice.other.forkjoin;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;

public class Simple {
    public static void main(String[] args) {
        List<MyTask> taskList = new LinkedList<>();

        for (int i = 0; i < 500; i++) {
            taskList.add(new MyTask());
        }
        ForkJoinPool.commonPool().invokeAll(taskList);

        System.exit(0);
    }
}

class MyTask implements Callable<String> {

    @Override
    public String call() {
        // 只有一个任务时，仅使用main线程，任务增多后，Worker线程数上升到4，再上升到5，最后到7稳定
        // commonPool 的活跃线程数是自动伸展的
        System.out.println("poolSize now " + ForkJoinPool.commonPool().getPoolSize());

        System.out.println(Thread.currentThread().getName());

        System.out.println("fork join pool task done ");
        return Thread.currentThread().getName();
    }
}