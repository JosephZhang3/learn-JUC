package Java_concurrency_in_practice.other.threadandrunnable;

import java.util.concurrent.atomic.AtomicInteger;

class ThreadTest extends Thread {
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
