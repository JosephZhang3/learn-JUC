package Java_concurrency_in_practice.chap2;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 一个非线程安全的计数器
 * 100个线程跑3个循环，这个过程没有出现任何问题，最终结果是300
 * 但是，500个线程跑3个循环，结果就不对了(期望是1800，实际是1753)
 * 如果万级以上用户(线程)同时操作，问题就会更加明显。典型的例子:电商超卖问题
 */
@WebServlet(urlPatterns = "/UnsafeCounter")
public class UnsafeCounter extends HttpServlet {

    private long count = 0;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ++count;
//        //这一条语句其实包含3个原子操作：
//        // 1.读取变量count的值
//        // 2.加1
//        // 3.把计算结果写入变量count
        System.out.println(count);
    }


//    public static void main(String[] args) {
//        int count = 0;
//        System.out.println(++count);//打印出1，变量先自增，然后作为表达式的值
//        System.out.println(count++);//打印出0，变量先作为表达式的值，然后自增
//    }
}
