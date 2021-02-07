package Java_concurrency_in_practice.chap4;

import net.jcip.annotations.ThreadSafe;

import java.util.Vector;

@ThreadSafe
public class BetterVector<E> extends Vector<E> {

    /**
     * 向量列表中不存在元素时，添加
     * 这个方法签名使用的修饰符 synchronized
     *
     * @param x
     * @return
     */
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !super.contains(x);
        if (absent) {
            super.add(x);
        }
        return absent;
    }

}
