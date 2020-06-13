package chap2;

import net.jcip.annotations.NotThreadSafe;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 使用 AtomicLong 保证线程安全的（假）因数分解器
 *
 * @author jianghao.zhang
 */
@WebServlet(urlPatterns = "/UnsafeCounter")
@NotThreadSafe
public class CountingFactorize implements Servlet {

    private final AtomicLong count = new AtomicLong(0);

    public Long getCount() {
        return count.get();
    }

    public void init(ServletConfig servletConfig) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        BigInteger[] factors = factor(i);
        count.incrementAndGet();
        encodeIntoResponse(servletResponse, factors);
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }

    void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {
    }
}
