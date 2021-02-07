package Java_concurrency_in_practice.chap2;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 一个线程安全的计数器
 */
@WebServlet(urlPatterns = "/safeCounter")
public class SafeCounter extends HttpServlet {

    /**
     * 使用线程安全类AtomicLong 原子化Long类型 来管理计数器的状态，保证代码的线程安全性
     * servlet的状态就是指计数器的状态，计数器是线程安全的 => 这个servlet也是线程安全的
     */
    private final AtomicLong count = new AtomicLong(0);

    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        long result = count.incrementAndGet();
        encodeIntoResponse(resp, result);
    }

    private void encodeIntoResponse(HttpServletResponse resp, Long result) {

        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type", "application/json;charset=UTF-8");
        PrintWriter pw = null;
        try {
            pw = resp.getWriter();
            System.out.println(result.toString());
            pw.write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();// 虽然servlet容器会最终自动关闭，但手动关闭是一种好习惯
            }
        }
    }
}
