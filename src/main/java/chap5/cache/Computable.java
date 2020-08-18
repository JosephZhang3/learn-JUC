package chap5.cache;

/**
 *
 *
 * @author jianghao.zhang
 */
public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
