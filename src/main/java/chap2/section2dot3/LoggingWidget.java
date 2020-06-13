package chap2.section2dot3;

public class LoggingWidget extends Widget {

    @Override
    public synchronized void doSomeThing() {
        System.out.println(toString() + ": calling superclass method doSomeThing");
        //父类中的方法的锁（内置锁）是可重入的，因此当线程试图获得一个已经由它自己持有的锁（父类中的内置锁），就能成功
        //如果不可重入，那么这里线程就会等待自己释放由自己持有的锁，这是不可能的，于是会出现死锁问题
        super.doSomeThing();
    }

    public static void main(String[] args) {
        LoggingWidget lw = new LoggingWidget();
        for (int i = 0; i < 500; i++) {
            lw.doSomeThing();
        }
    }
}
