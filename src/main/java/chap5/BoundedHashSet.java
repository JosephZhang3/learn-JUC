package chap5;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 有界阻塞容器，借助信号量实现
 *
 * @author jianghao.zhang
 */
public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore sem;

    /**
     * @param bound 初始化容器容量的最大值
     */
    public BoundedHashSet(int bound) {
        // 给集合加上同步机制
        this.set = Collections.synchronizedSet(new HashSet<>());
        // "许可"数量的最大值也是容器容量的最大值
        sem = new Semaphore(bound);
    }

    /**
     * 添加元素
     */
    public boolean add(T o) throws InterruptedException {
        // 获取一个许可
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                sem.release();// 释放一个许可，这里是对 acquire 造成的影响的补偿
            }
        }
    }

    /**
     * 移除元素
     */
    public boolean remove(T o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved) {
            // 释放一个许可
            sem.release();
        }
        return wasRemoved;
    }
}
