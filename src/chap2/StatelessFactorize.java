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
 */
@WebServlet(name = "StatelessFactorize")
public class StatelessFactorize extends HttpServlet {

    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);//完成分解后的因数
        encodeIntoResponse(resp, factors);
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
     * 这是一个假的因数分解器，QAQ
     *
     * @param i
     * @return
     */
    private BigInteger[] factor(BigInteger i) {
        return new BigInteger[]{i};
    }

    /**
     * 模拟把因数分解结果编码进响应
     *
     * @param resp
     * @param factors
     */
    private void encodeIntoResponse(HttpServletResponse resp, BigInteger[] factors) {

        resp.setCharacterEncoding("utf-8");
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        try {
            PrintWriter pw = resp.getWriter();
            pw.write("返回一点东西");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
