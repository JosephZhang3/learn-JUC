package threadandrunnable;

public class Thread_direct_start {
    public static void main(String[] args) {
        ThreadTest t = new ThreadTest();
        t.start();
        t.start();
        t.start();
        t.start();
        t.start();
/*
控制台输出结果如下：

Exception in thread "main" java.lang.IllegalThreadStateException
	at java.lang.Thread.start(Thread.java:708)
	at threadandrunnable.MyThread.main(MyThread.java:7)
Thread id is 11 ,Thread name is Thread-0 ,this is the 10 time call run method.
Thread id is 11 ,Thread name is Thread-0 ,this is the 9 time call run method.
Thread id is 11 ,Thread name is Thread-0 ,this is the 8 time call run method.
Thread id is 11 ,Thread name is Thread-0 ,this is the 7 time call run method.
Thread id is 11 ,Thread name is Thread-0 ,this is the 6 time call run method.
Thread id is 11 ,Thread name is Thread-0 ,this is the 5 time call run method.
Thread id is 11 ,Thread name is Thread-0 ,this is the 4 time call run method.
Thread id is 11 ,Thread name is Thread-0 ,this is the 3 time call run method.
Thread id is 11 ,Thread name is Thread-0 ,this is the 2 time call run method.
Thread id is 11 ,Thread name is Thread-0 ,this is the 1 time call run method.

Process finished with exit code 1

调用多次start方法，实际上只启动了一个线程，还抛了异常 IllegalThreadStateException
而当把代码修改成仅仅调用一次start方法，就不会抛异常，输出跟之前调多次一致。
这是为什么？
我们可以debug跟进到报异常的代码，如下：

public synchronized void start() {
         * A zero status value corresponds to state "NEW".
        if (threadStatus != 0)
            throw new IllegalThreadStateException();

因为先前调用过一次start方法，线程的状态由初始化的0变成了RUNNABLE非0，所以在第二次调用时，会throw异常结束主线程main方法
*/
    }
}

class ThreadTest extends Thread {
    private int n = 10;

    public void run() {
        while (n > 0) {
            System.out.println(
                    "Thread id is " +
                            Thread.currentThread().getId() + " ,"
                            + "Thread name is " +
                            Thread.currentThread().getName()
                            + " ,this is the " + n-- + " time call run method.");// n先作为表达式的值，然后再自减
        }
    }
}