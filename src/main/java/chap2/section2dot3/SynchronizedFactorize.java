package chap2.section2dot3;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

/**
 * 虽然是完全线程安全的，但是会有线程活跃性 liveness 问题（某个时刻只能有一个线程执行同步代码块）
 *
 * @author jianghao.zhang
 */
@ThreadSafe
public class SynchronizedFactorize implements Servlet {
    @GuardedBy("this")
    private BigInteger lastNumber;
    @GuardedBy("this")
    private BigInteger[] lastFactors;

    public void init(ServletConfig servletConfig) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 注意：使用了同步关键字 synchronized
     */
    public synchronized void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);
        if (lastNumber.equals(i)) {
            encodeIntoResponse(servletResponse, lastFactors);
        }
        BigInteger[] factors = factor(i);
        // 重置缓存
        lastNumber = i;
        lastFactors = factors;
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
