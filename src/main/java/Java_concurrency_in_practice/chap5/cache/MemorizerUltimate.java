package Java_concurrency_in_practice.chap5.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 异步任务，防止"无沟通"的情况下，多个线程计算相同参数
 *
 * @author jianghao.zhang
 */
public class MemorizerUltimate<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> compute;

    public MemorizerUltimate(Computable<A, V> compute) {
        this.compute = compute;
    }

    /**
     * "if null then put" ,这样的操作是非原子的，因此仍然有可能有两个线程在同一时间内调用 compute 来计算相同的值
     */
    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        // 判断缓存中是否有正在计算此参数arg的任务
        if (f == null) {
            FutureTask<V> ft = new FutureTask<>(() -> compute.compute(arg));
            // 把 ft 的引用给予 f ，然后调用 f.get() 就等价于调用 ft.get()
            f = ft;

            // 把新开启的任务放进缓存
            cache.put(arg, ft);
            ft.run();
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
