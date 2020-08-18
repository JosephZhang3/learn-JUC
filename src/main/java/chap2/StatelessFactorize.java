package chap2;

import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

/**
 * 一个无状态的因数分解器
 *
 * @author jianghao.zhang
 */
@WebServlet(urlPatterns = "/statelessFactorize")
public class StatelessFactorize extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        BigInteger i = extractFromRequest(req);// i 是一个"无状态"的对象，因为它是临时生成的，而不是每个线程都能访问到的field
        BigInteger[] factors = factor(i);// 完成分解后的因数
        encodeIntoResponse(resp, factors);// print  3	5
    }

    /**
     * 模拟从请求中获取输入（被分解数）
     *
     * @param req
     * @return
     */
    private BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    /**
     * 模拟一个因数分解器
     *
     * @param i
     * @return
     */
    private BigInteger[] factor(BigInteger i) {
        return new BigInteger[]{new BigInteger("3"),new BigInteger("5")};
    }

    /**
     * 模拟把因数分解结果编码进响应
     *
     * @param resp
     * @param factors
     */
    private static void encodeIntoResponse(HttpServletResponse resp, BigInteger[] factors) {

        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        try {
            PrintWriter pw = resp.getWriter();
            pw.write(factors[0].toString()+"\t"+factors[1].toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
