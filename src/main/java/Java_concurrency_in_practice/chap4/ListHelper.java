package Java_concurrency_in_practice.chap4;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ThreadSafe
public class ListHelper<E> {
    public final List<E> list = Collections.synchronizedList(new ArrayList<>());

    /**
     * 这个方法签名使用了修饰符 synchronized ，但是别误以为这样就能保证线程安全了
     * 因为你这里加上的锁只针对 ListHelper类的putIfAbsent方法，跟加在 list.contains 和 list.add 方法上的锁完全是两个不同的东西
     *
     * @param x
     * @return
     */
 /*   public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);
        if (absent) {
            list.add(x);
        }
        return absent;
    }*/

    /**
     * list对象上的操作，现在也归putIfAbsent上的锁保护，是同一把锁，所以能保证线程安全性
     *
     * @param x
     * @return
     */
    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent) {
                list.add(x);
            }
            return absent;
        }
    }
}
