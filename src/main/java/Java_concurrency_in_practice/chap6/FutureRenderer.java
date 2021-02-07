package Java_concurrency_in_practice.chap6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 使用 Future 等待图像下载
 *
 * @author jianghao.zhang
 */
public class FutureRenderer {
    /**
     * 以 CPU 的核心数量作为线程池固定大小
     */
    private final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    void renderPage(CharSequence source) {
        ImageInfo[] imageInfos = scanForImageInfo(source);
        // 定义task（下载image）
        Callable<List<ImageData>> task = () -> {
            List<ImageData> result = new ArrayList<>();
            for (ImageInfo i : imageInfos) {
                // 注意：这个地方仍然是串行的
                result.add(i.downloadImage());
            }
            return result;
        };

        // 提交task给其它线程执行
        Future<List<ImageData>> downloadTask = es.submit(task);

        // 渲染text
        renderText(source);

        // 获取下载结果，紧接着渲染image
        try {
            List<ImageData> imageDataList = downloadTask.get();
            for (ImageData i : imageDataList) {
                renderImage(i);
            }
        } catch (InterruptedException e) {
            // case1:任务被中断

            // 中断最外部的提交任务的线程
            Thread.currentThread().interrupt();
            // 取消执行任务的线程，运行取消时被中断
            downloadTask.cancel(true);
        } catch (ExecutionException e) {
            // case2:任务执行过程出现异常
            System.out.println("原始异常：" + e.getCause());
        }
    }

    private void renderImage(ImageData data) {
    }

    private ImageInfo[] scanForImageInfo(CharSequence source) {
        return new ImageInfo[0];
    }

    private void renderText(CharSequence source) {
    }
}
