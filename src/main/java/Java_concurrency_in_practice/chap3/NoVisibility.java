package Java_concurrency_in_practice.chap3;

public class NoVisibility {

    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();//放弃
            }
            System.out.println("current number is " + number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
        // 一般的情况下，输出42，并不会看出来书里面说的"重排序"现象
    }
}
