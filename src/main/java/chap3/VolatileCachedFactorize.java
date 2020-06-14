package chap3;

import net.jcip.annotations.Immutable;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

@WebServlet(urlPatterns = "/VolatileCachedFactorize")
public class VolatileCachedFactorize implements Servlet {

    private volatile OneValueCache cache = new OneValueCache(null, null);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger i = extractFromRequest(servletRequest);

        // 首先从缓存中拿，没拿到再考虑动手做一次分解因数
        BigInteger[] factors = cache.getFactors(i);
        if (factors == null) {
            System.out.println("缓存未命中");
            factors = factor(i);
            cache = new OneValueCache(i, factors);
        } else {
            System.out.println("缓存命中了");
        }

        encodeIntoResponse(servletResponse, factors);
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
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

@Immutable
class OneValueCache {

    private final BigInteger lastNumber;
    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger lastNumber, BigInteger[] lastFactors) {
        this.lastNumber = lastNumber;
        if (lastFactors != null) {
            this.lastFactors = Arrays.copyOf(lastFactors, lastFactors.length);
        } else {
            this.lastFactors = new BigInteger[]{};
        }
    }

    public BigInteger[] getFactors(BigInteger source) {
        // lastNumber == null 的情形是：第一次请求读缓存
        // !lastNumber.equals(i) 的情形是：缓存里存储的上次的源数（待分解数）跟这次要分解的数不一样，即未命中
        if (lastNumber == null || !lastNumber.equals(source)) {
            return null;
        }
        return lastFactors.clone();
    }
}