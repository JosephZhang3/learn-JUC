package chap5.cache;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author jianghao.zhang
 */
public class Factorizer implements Servlet {

    private final Computable<BigInteger, BigInteger[]> c = this::factor;

    private final Computable<BigInteger, BigInteger[]> cache = new MemorizerFinal<>(c);

    public static void main(String[] args) {
        Factorizer f = new Factorizer();
        try {
            f.service(null, null);
            f.service(null, null);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算函数
     */
    private BigInteger[] factor(BigInteger arg) {
        // 空实现
        System.out.println("now compute factor of arg:" + arg);
        return new BigInteger[]{new BigInteger("423"), new BigInteger("323")};
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        BigInteger[] result = new BigInteger[0];
        try {
            BigInteger i = new BigInteger("83289");
            result = cache.compute(i);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw e;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The result of compute is " + Arrays.toString(result));
//        encodeIntoResponse((HttpServletResponse) servletResponse, result);
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
    }

    /**
     * 模拟把因数分解结果编码进响应
     */
    private static void encodeIntoResponse(HttpServletResponse resp, BigInteger[] factors) {
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        try {
            PrintWriter pw = resp.getWriter();
            pw.write(factors[0].toString() + "\t" + factors[1].toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
