package Java_concurrency_in_practice.chap5.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 记忆器，帮助缓存计算结果
 *
 * @author jianghao.zhang
 */
public class MemorizerFoo<A, V> implements Computable<A, V> {

    /**
     * 计算结果容器
     */
    private final Map<A, V> cache = new HashMap<A, V>();
    /**
     * 针对的计算
     */
    private final Computable<A, V> comp;

    public MemorizerFoo(Computable<A, V> comp) {
        this.comp = comp;
    }

    /**
     * 特别注意关键词 synchronized ，使用同步机制保证 HashMap cache 不被两个线程同时操作
     * 但问题非常明显，如果 comp.compute(arg) 这行代码的执行时间非常漫长，那么其它正在排队
     * 的线程就会被阻塞同样长的时间。
     * 如果排队的线程很多，甚至还不如让每个线程分开计算给定的arg，一旦完成立即把结果送回；后者更高效。
     * 总之这种写法的并发性很糟糕。
     *
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = comp.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
