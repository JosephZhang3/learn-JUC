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

    // 给任务绑定线程
    private final Thread t = new Thread(futureTask);

    // 启动线程（即开始任务）
    public void start() {
        t.start();
    }

    // 查询任务跑完的结果，如果没跑完，阻塞主线程（停滞）直到得到结果或者超时
    public Product getResult() throws ExecutionException, InterruptedException {
        return futureTask.get();
    }

    public static void main(String[] args) throws InterruptedException {
        Preloader preloader = new Preloader();
        preloader.start();

        try {
            Product p = preloader.getResult();
            System.out.println("立即拿到的结果:" + p.getName());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(3000);
            Product p = preloader.getResult();
            System.out.println("延迟3秒拿到的结果:" + p.getName());
        } catch (ExecutionException | InterruptedException e) {
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