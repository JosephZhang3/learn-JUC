package chap3;

public class CountingSheep extends Thread {
    volatile boolean asleep;

    @Override
    public void run() {
        while (!asleep) {
            countSomeSheep();
        }
    }

    void countSomeSheep() {
        System.out.println("I see one sheep");
    }
}
