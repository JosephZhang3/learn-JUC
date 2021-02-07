package Java_concurrency_in_practice.chap4;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SafePoint {
    private int x, y;

    public SafePoint(int[] a) {
        this.x = a[0];
        this.y = a[1];
    }

    public SafePoint(SafePoint s) {
        this(s.get());
    }

    public synchronized int[] get() {
        return new int[]{x, y};
    }

    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 我们来分析为什么这个类是线程安全的
     * 很精髓的一点，get()方法同时获得x和y的值放到一个数组中返回，如果为x和y分别提供get()方法，就会出现看到x和y状态不一致的问题
     * get()和set()方法都是 synchronized 修饰
     */
}
