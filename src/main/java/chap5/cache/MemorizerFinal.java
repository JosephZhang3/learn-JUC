package chap5.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 最终实现
 *
 * @author jianghao.zhang
 */
public class MemorizerFinal<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> compute;

    public MemorizerFinal(Computable<A, V> compute) {
        this.compute = compute;
    }

    /**
     * "if null then put" ,这样的操作是非原子的，因此仍然有可能有两个线程在同一时间内调用 compute 来计算相同的值
     * 用 putIfAbsent 原子操作就不可能有两个线程对同一个arg做出 为null然后put 的操作
     */
    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        // 判断缓存中是否有正在计算此参数arg的任务
        if (f == null) {
            FutureTask<V> ft = new FutureTask<>(() -> compute.compute(arg));

            // 把新创建的与arg关联的任务放进缓存（注意：原子操作）
            f = cache.putIfAbsent(arg, ft);

            // 如果 putIfAbsent 原子操作判断出缓存中原先不存在与arg关联的任务，则把 ft 的引用给予 f，然后启动
            if (f == null) {
                f = ft;
                ft.run();
            }
        }

        V v = null;
        try {
            // 经历过前面代码的操作，缓存中必然已经有了此arg的计算任务。这个任务有可能是已经结束的，也可能正在运行中。
            // 如果已经结束，直接返回计算结果。如果正在运行，当前这个线程会阻塞，直到计算结束返回结果。
            v = f.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return v;
    }
}
