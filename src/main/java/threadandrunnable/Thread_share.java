package threadandrunnable;

import java.util.concurrent.atomic.AtomicInteger;

public class Thread_share {
    public static void main(String[] args) {
        RunnableTest t = new RunnableTest();
        new Thread(t).start();
        new Thread(t).start();
        new Thread(t).start();
//        new Thread(t).start();
//        new Thread(t).start();

/*
控制台输出结果如下：

Thread id is 12 ,Thread name is Thread-1 ,this is the 10 time call run method.
Thread id is 13 ,Thread name is Thread-2 ,this is the 9 time call run method.
Thread id is 13 ,Thread name is Thread-2 ,this is the 6 time call run method.
Thread id is 13 ,Thread name is Thread-2 ,this is the 5 time call run method.
Thread id is 14 ,Thread name is Thread-3 ,this is the 8 time call run method.
Thread id is 14 ,Thread name is Thread-3 ,this is the 3 time call run method.
Thread id is 13 ,Thread name is Thread-2 ,this is the 4 time call run method.
Thread id is 13 ,Thread name is Thread-2 ,this is the 1 time call run method.
Thread id is 12 ,Thread name is Thread-1 ,this is the 7 time call run method.
Thread id is 14 ,Thread name is Thread-3 ,this is the 2 time call run method.

Process finished with exit code 0

new 关键字创建了3个线程 Thread-1 Thread-2 Thread-3，这3个线程执行ThreadTest3实例的run方法时，共享资源变量 n
如果再多 new 2个线程，就会有5个线程共享资源变量 n
*/
    }
}

class RunnableTest implements Runnable {
    private final AtomicInteger n = new AtomicInteger(10);

    @Override
    public void run() {
        while (n.get() > 0) {
            System.out.println(
                    "Thread id is " +
                            Thread.currentThread().getId() + " ,"
                            + "Thread name is " +
                            Thread.currentThread().getName()
                            + " ,this is the " + n.getAndDecrement() + " time call run method.");// n先作为表达式的值，然后再自减
        }
    }
}