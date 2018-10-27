package main;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 一个非线程安全的计数器
 */
@WebServlet(name = "UnsafeCounter")
public class UnsafeCounter extends HttpServlet {

    private long count = 0;

    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        ++count;
        //这一条语句其实包含3个原子操作：
        // 1.读取变量count的值
        // 2.加1
        // 3.把计算结果写入变量count
    }



//    public static void main(String[] args) {
//        int count = 0;
//        System.out.println(++count);//打印出1，变量先自增，然后作为表达式的值
//        System.out.println(count++);//打印出0，变量先作为表达式的值，然后自增
//    }
}
