package chap2;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义定时器
 */
public class CustomTimer {

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("定时任务线程 id: " + Thread.currentThread().getId() + "\tname: " + Thread.currentThread().getName());
                schedule();
            }
        };
        System.out.println("run到了这里");
        System.out.println("主线程 id: " + Thread.currentThread().getId() + "\tname: " + Thread.currentThread().getName());
        timer.schedule(task, 5000);
    }

    private static void schedule() {
        System.out.println("定时器执行了一次");
        System.exit(0);
    }

}
