package chap8;

import java.util.concurrent.*;

/**
 * 演示线程死锁
 *
 * @author jianghao.zhang
 */
public class ThreadDeadLock {
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * 定义线程任务 读取文件
     */
    public class LoadFileTask implements Callable<String> {
        private final String fileName;

        public LoadFileTask(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public String call() throws Exception {
            System.out.println("loaded the file:" + fileName);
            return null;
        }
    }

    /**
     * 定义线程任务 读取源代码文件然后渲染出页面
     */
    public class RenderPageTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            Future<String> headerFileLoad, footerFileLoad;

            // 提交子任务
            headerFileLoad = executorService.submit(new LoadFileTask("header"));
            footerFileLoad = executorService.submit(new LoadFileTask("footer"));

            // 渲染主体部分
            String page = renderBody();

            // 阻塞等待子任务的执行结果，因为有可能子任务无法正确执行结束，所以有概率发生死锁
            return headerFileLoad.get() + page + footerFileLoad.get();
        }
    }

    private String renderBody() {
        return null;
    }
}