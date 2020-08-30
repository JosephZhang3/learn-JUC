package chap6;

import java.util.concurrent.*;

/**
 * 为任务设置时限
 *
 * @author jianghao.zhang
 */
public class TimeLimitAd {
    /**
     * 以 CPU 的核心数量作为线程池固定大小
     */
    private final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final Long TIME_BUDGET = 5000000000L;
    private static final double TIME_NANO_FACTOR = 1000000000.0;

    private final Ad DEFAULT_AD = new Ad("默认广告");

    public static void main(String[] args) {
        Page p = new TimeLimitAd().renderPageWithAd();
        System.out.println("广告名：" + p.getAd().getName());
        System.exit(0);
    }

    Page renderPageWithAd() {
        // 5秒限制
        long t = System.nanoTime() + TIME_BUDGET;

        // 提交任务，并行执行
        Future<Ad> task = es.submit(this::fetchAdTask);

        // 渲染页面主要部分，非广告
        Page p = renderPageMain();

        try {
            long limit = t - System.nanoTime();
            // 以指定时间限制等待拿到结果
            Ad ad = task.get(limit, TimeUnit.NANOSECONDS);

            System.out.println("限制 " + limit / TIME_NANO_FACTOR + " 秒内必须计算出结果");
            System.out.println("实际耗时 " + (System.nanoTime() - t + TIME_BUDGET) / TIME_NANO_FACTOR + " 秒");

            // 给页面填充广告
            p.setAd(ad);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("没取到，任务执行异常");
            p.setAd(DEFAULT_AD);
        } catch (TimeoutException e) {
            System.out.println("没取到，超时");
            p.setAd(DEFAULT_AD);
            task.cancel(true);
        }

        return p;
    }

    private Page renderPageMain() {
        return new Page(null);
    }

    Ad fetchAdTask() {
        try {
            // 假执行8秒
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Ad("真实广告");
    }

    private final class Page {
        private Ad ad;

        public Ad getAd() {
            return ad;
        }

        public void setAd(Ad ad) {
            this.ad = ad;
        }

        public Page(Ad ad) {
            this.ad = ad;
        }
    }

    private final class Ad {
        private String name;

        public Ad(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
