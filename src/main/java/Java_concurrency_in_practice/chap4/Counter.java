package Java_concurrency_in_practice.chap4;

/**
 * 应用了Java监视器模式
 *
 * step1：构成对象Counter的所有field只有sheepCnt
 * step2：约束field sheepCnt的不变性条件，increase方法和getValue方法必须合起来是一个原子操作
 * step3：并发访问管理策略->给这两个方法加 synchronized 关键字,JVM级别同步
 * 补充：必须将同步策略写成正式文档，后续才有分析与维护的可能
 */
public final class Counter {// final修饰的类没有被继承的可能
    private long sheepCnt = 0;

    public synchronized long getValue() {
        return sheepCnt;
    }

    public synchronized long increase() {
        if (sheepCnt == Long.MAX_VALUE) {// 不变性条件，用来判断状态field sheepCnt是有效的还是无效的
            throw new IllegalStateException("count overflow");
        }
        return ++sheepCnt;// 先自增以改变变量自身值，然后作为表达式的结果
    }
}
