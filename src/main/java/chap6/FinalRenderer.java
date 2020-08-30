package chap6;

import java.util.concurrent.*;

/**
 * 使用 CompletionService 实现页面渲染
 * 把串行任务分解为并行
 *
 * @author jianghao.zhang
 */
public class FinalRenderer {
    /**
     * 以 CPU 的核心数量作为线程池固定大小
     */
    private final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    void renderPage(CharSequence source) {
        ImageInfo[] imageInfos = scanForImageInfo(source);

        // 完成任务的服务，低层是一个 LinkedBlockingQueue<Future<ImageData>> ，队列容量限制为 int 的最大值
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(es);

        // 所有下载任务并行执行
        for (ImageInfo i : imageInfos) {
            completionService.submit(i::downloadImage);
        }

        // 渲染text
        renderText(source);

        try {
            ImageData imageData;
            int n = imageInfos.length;
            // 有多少张图片，阻塞队列中就有多少个元素
            while (n > 0) {
                // 获取下载结果。最先submit的任务最先被execute，最后也最先被take取出
                imageData = completionService.take().get();
                // 渲染一张image
                renderImage(imageData);
                n--;
            }
        } catch (InterruptedException e) {
            // case1:任务被中断

            // 中断最外部的提交任务的线程
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            // case2:任务执行过程出现异常，原始异常信息全都在 cause 里面
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
