package Java_concurrency_in_practice.chap5.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 记忆器，帮助缓存计算结果
 *
 * @author jianghao.zhang
 */
public class MemorizerBar<A, V> implements Computable<A, V> {

    /**
     * 计算结果容器（并发容器）
     */
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    /**
     * 针对的计算
     */
    private final Computable<A, V> comp;

    public MemorizerBar(Computable<A, V> comp) {
        this.comp = comp;
    }

    /**
     * 注意我们使用了并发容器 ConcurrentHashMap 来存放结果，替换了之前的MemorizerFoo中的同步机制
     * 但问题依然存在，线程之间并不知道彼此手头正在计算的arg，有可能他们中的好几个都在计算同一个arg，
     * 此时就会出现"并行计算相同值"这类不必要的耗时
     *
     * @param arg
     * @return
     * @throws InterruptedException
     */
    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            result = comp.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
