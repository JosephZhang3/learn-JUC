package chap5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Preloader {

    private Product loadProduct() {
        return new Product("demo product");
    }

    // 定义任务
    private final FutureTask<Product> futureTask = new FutureTask<Product>(new Callable() {
        @Override
        public Object call() throws InterruptedException {
            System.out.println("睡2秒，此时futureTask的get方法被阻塞");
            Thread.sleep(2000);
            return loadProduct();
        }
    });

    // 给任务绑定副线程
    private final Thread t = new Thread(futureTask);

    /**
     * 启动线程（即开始任务）
     */
    public void start() {
        t.start();
    }

    /**
     * 查询任务跑完的结果，如果没跑完，会阻塞主线程（停滞）直到得到结果或者超时
     */
    public Product getResult() throws DataLoadException, InterruptedException {
        try {
            return futureTask.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException) {
                // 已知的 checked 异常，直接抛出
                throw (DataLoadException) cause;
            } else {
                // 尝试抛出 unchecked 异常，可能判断出不是 unchecked 异常，那么就抛出 IllegalStateException
                throw launderThrowable(cause);
            }
        }
    }

    /**
     * 清洗异常，针对 unchecked exception（未检查异常）
     * 如果是运行时异常，返回
     * 如果是 Error ，直接抛出
     * 如果是 已检查异常，抛出非法状态
     */
    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {// 继承自 TestHarness 的 unchecked 类型的异常
            return (RuntimeException) t;
        } else if (t instanceof Error) {// 继承自 Exception 但是 不继承自 RuntimeException 的 unchecked 类型的异常
            throw (Error) t;
        } else {
            throw new IllegalStateException("not unchecked", t);
        }
    }

    public static void main(String[] args) {
        Preloader preloader = new Preloader();
        preloader.start();

        try {
            Product p = preloader.getResult();
            System.out.println("立即拿到的结果:" + p.getName());
        } catch (InterruptedException | DataLoadException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(3000);
            Product p = preloader.getResult();
            System.out.println("延迟3秒拿到的结果:" + p.getName());
        } catch (InterruptedException | DataLoadException e) {
            e.printStackTrace();
        }
    }
}

class Product {

    public Product(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * checked exception ，已检查异常，继承自 Exception
 */
class DataLoadException extends Exception {
}