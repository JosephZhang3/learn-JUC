package main;

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
@WebServlet(name = "SafeConter")
public class SafeConter extends HttpServlet {

    /**
     * 使用线程安全类AtomicLong来管理计数器的状态，从而确保了代码的线程安全性。
     * servlet的状态就是指计数器的状态，因为计数器是线程安全的，所以这里servlet也是线程安全的。
     */
    private final AtomicLong count = new AtomicLong(0);

    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        long result = count.incrementAndGet();
        encodeIntoResponse(resp, result);
    }

    private void encodeIntoResponse(HttpServletResponse resp, Long result) {

        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        try {
            PrintWriter pw = resp.getWriter();
            pw.write(result.toString());
            //不用手动关闭字符流，servlet容器会最终自动关闭
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
