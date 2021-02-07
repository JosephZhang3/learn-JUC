package Java_concurrency_in_practice.chap2;

/**
 * 懒加载中存在的竞态条件(单例模式--懒汉模式)
 */
public class LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;
    }
}

class ExpensiveObject {
}