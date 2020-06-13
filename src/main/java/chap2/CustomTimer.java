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
                schedule();
            }
        };
        System.out.println("run到了这里");
        timer.schedule(task, 5000);
    }

    private static void schedule() {
        System.out.println("定时器执行了一次");
    }

}
