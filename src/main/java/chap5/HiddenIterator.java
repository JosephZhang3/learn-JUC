package chap5;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HiddenIterator {
    private final Set<Integer> set = new HashSet<>();

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public synchronized void remove(Integer i) {
        set.remove(i);
    }

    public void foo() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            add(r.nextInt());
        }
        System.out.println("DEBUG:added ten elements to " + set);
    }

    public static void main(String[] args) {
        // 多个线程同时调用foo方法，会造成并发修改异常。因为相当于 add ，for迭代 同时进行。
        new HiddenIterator().foo();
    }
}
