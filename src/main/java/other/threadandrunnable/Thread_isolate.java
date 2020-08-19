package other.threadandrunnable;

public class Thread_isolate {
    public static void main(String[] args) {
        new ThreadTest().start();
        new ThreadTest().start();
        new ThreadTest().start();
//        new ThreadTest2().start();
//        new ThreadTest2().start();
/*
 * 控制台输出结果如下：
Thread id is 12, Thread name is Thread-1, this is the 10 time call run method.
Thread id is 11, Thread name is Thread-0, this is the 10 time call run method.
Thread id is 11, Thread name is Thread-0, this is the 9 time call run method.
Thread id is 11, Thread name is Thread-0, this is the 8 time call run method.
Thread id is 13, Thread name is Thread-2, this is the 10 time call run method.
Thread id is 11, Thread name is Thread-0, this is the 7 time call run method.
Thread id is 12, Thread name is Thread-1, this is the 9 time call run method.
Thread id is 12, Thread name is Thread-1, this is the 8 time call run method.
Thread id is 12, Thread name is Thread-1, this is the 7 time call run method.
Thread id is 11, Thread name is Thread-0, this is the 6 time call run method.
Thread id is 11, Thread name is Thread-0, this is the 5 time call run method.
Thread id is 11, Thread name is Thread-0, this is the 4 time call run method.
Thread id is 11, Thread name is Thread-0, this is the 3 time call run method.
Thread id is 13, Thread name is Thread-2, this is the 9 time call run method.
Thread id is 13, Thread name is Thread-2, this is the 8 time call run method.
Thread id is 13, Thread name is Thread-2, this is the 7 time call run method.
Thread id is 13, Thread name is Thread-2, this is the 6 time call run method.
Thread id is 13, Thread name is Thread-2, this is the 5 time call run method.
Thread id is 11, Thread name is Thread-0, this is the 2 time call run method.
Thread id is 12, Thread name is Thread-1, this is the 6 time call run method.
Thread id is 12, Thread name is Thread-1, this is the 5 time call run method.
Thread id is 12, Thread name is Thread-1, this is the 4 time call run method.
Thread id is 12, Thread name is Thread-1, this is the 3 time call run method.
Thread id is 11, Thread name is Thread-0, this is the 1 time call run method.
Thread id is 13, Thread name is Thread-2, this is the 4 time call run method.
Thread id is 13, Thread name is Thread-2, this is the 3 time call run method.
Thread id is 13, Thread name is Thread-2, this is the 2 time call run method.
Thread id is 12, Thread name is Thread-1, this is the 2 time call run method.
Thread id is 13, Thread name is Thread-2, this is the 1 time call run method.
Thread id is 12, Thread name is Thread-1, this is the 1 time call run method.

仔细观察就可以发现，new 出来的3个线程 Thread-0 Thread-1 Thread-2 ，但是这3个线程互不影响，每个都独占一份资源变量 n ，从初始的10递减到1
 */
    }
}