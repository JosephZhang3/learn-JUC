package chap5;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 元胞自动机（未完成 TODO）
 * 通俗理解：人满发车，到终点瞬移到起点，重新装人发车
 *
 * @author jianghao.zhang
 */
public class CellularAutomata {
    private final CyclicBarrier barrier;
    private final Worker[] workers;
    private final Board mainBoard;

    public CellularAutomata(Board mainBoard) {
        this.mainBoard = mainBoard;

        // CPU核心数量
        int count = Runtime.getRuntime().availableProcessors();
        // 栅栏
        this.barrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                mainBoard.commitNewValues();
            }
        });

        // 设定工作线程的数量为核心数
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
        }

    }

    /**
     * 内部类，执行具体任务的线程
     */
    private class Worker implements Runnable {
        private final Board board;

        public Worker(Board board) {
            this.board = board;
        }

        @Override
        public void run() {
            // 自旋条件：没有收敛
            while (!board.hasConverged()) {
                for (int x = 0; x < board.getMaxX(); x++) {
                    for (int y = 0; y < board.getMaxY(); y++) {
                        board.setNewValue(x, y, computeValue(x, y));
                    }
                }
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException ex) {
                    return;
                }
            }
        }

        private int computeValue(int x, int y) {
            // Compute the new value that goes in (x,y)
            System.out.println("computeValue x + y" + x + y);
            return x + y;
        }

        /**
         * 启动任务
         */
        private void start() {
            for (int i = 0; i < workers.length; i++) {
                new Thread(workers[i]).start();
            }

            mainBoard.waitForConvergence();
        }
    }
}


/**
 * 细胞类，方法内容都是瞎写的
 */
class Board {
    private int x, y, value;

    public void setNewValue(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    //converge  英[kənˈvɜ:dʒ] 聚集
    public boolean hasConverged() {
        //TODO
        return true;
    }

    public int getMaxX() {
        return 100;
    }

    public int getMaxY() {
        return 100;
    }

    //计算新的值
    public void commitNewValues() {
        //TODO
    }

    //衍生子细胞
    public Board getSubBoard(int count, int i) {
        Board newBoard = new Board();
        newBoard.setNewValue(i, i, count);
        return newBoard;
    }

    /**
     * 等待收敛（归并）
     */
    public void waitForConvergence() {

    }
}