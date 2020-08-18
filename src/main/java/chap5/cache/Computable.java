package chap5.cache;

/**
 *
 *
 * @author jianghao.zhang
 */
@FunctionalInterface
public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
