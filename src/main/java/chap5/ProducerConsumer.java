package chap5;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;

public class ProducerConsumer {

    // 并发安全set，存储已经放入索引的文件的全路径名
    private final static CopyOnWriteArraySet<String> indexedFiles = new CopyOnWriteArraySet<>();

    /**
     * 爬取器
     */
    static class FileCrawler implements Runnable {

        private final BlockingDeque<File> fileQueue;
        private final FileFilter fileFilter;
        private final File root;

        FileCrawler(BlockingDeque<File> fileQueue, FileFilter fileFilter, File root) {
            this.fileQueue = fileQueue;
            this.fileFilter = pathname -> pathname.isDirectory() || fileFilter.accept(pathname);
            this.root = root;
        }

        private boolean alreadyIndexed(File f) {
            return indexedFiles.contains(f.getAbsolutePath());
        }

        @Override
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void crawl(File root) throws InterruptedException {
            File[] entries = root.listFiles(fileFilter);
            if (entries != null) {
                for (File entry : entries) {
                    if (entry.isDirectory()) {
                        crawl(entry);
                    } else if (!alreadyIndexed(entry)) {
                        System.out.println("线程名称 <" + Thread.currentThread().getName() + "> CRAWL " + entry.getName());
                        fileQueue.put(entry);
                        indexedFiles.add(entry.getAbsolutePath());
                    }
                }
            }
        }

    }

    /**
     * 索引器
     */
    static class Indexer implements Runnable {

        private final BlockingDeque<File> queue;

        Indexer(BlockingDeque<File> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            // 自旋锁，永不停止
            while (true) {
                try {
                    indexFile(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void indexFile(File file) {
            System.out.println("线程名称 <" + Thread.currentThread().getName() + "> INDEX " + file.getName());
        }
    }

    // --------------------- 测试代码 ---------------------

    private static final int BOUND = 10;// 队列的界
    private static final int N_CONSUMERS = Runtime.getRuntime().availableProcessors();// CPU核心数

    public static void startIndexing(File[] roots) {
        BlockingDeque<File> queue = new LinkedBlockingDeque<>(BOUND);
        FileFilter filter = pathname -> true;

        // 启动生产者线程
        for (File f : roots) {
            new Thread(new FileCrawler(queue, filter, f)).start();
        }

        // 启动消费者线程
        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new Indexer(queue)).start();
        }
    }

    public static void main(String[] args) {
        File[] files = new File[2];// 其实是两个目录
        files[0] = new File("/Users/mac/IdeaProjects/mcs-admin");
        files[1] = new File("/Users/mac/IdeaProjects/scfs");
        startIndexing(files);
    }
}
