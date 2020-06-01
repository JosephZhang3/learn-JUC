package chap2;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义定时器
 */
public class customTimer {

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                schedual();
            }
        };
        System.out.println("run到了这里");
        timer.schedule(task, 5000);
    }

    private static void schedual() {
        System.out.println("定时器执行了一次");
    }

}
