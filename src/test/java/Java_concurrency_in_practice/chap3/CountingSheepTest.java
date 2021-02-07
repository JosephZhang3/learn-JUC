package Java_concurrency_in_practice.chap3;

import org.junit.jupiter.api.Test;

public class CountingSheepTest {

    @Test
    public void testCountingSheep() {
        // junit 测试方法里的所有对象都会被它代理，因此这里调用hashCode方法传递给String类的参数
        // 并不是fdsf..字符串，而是junit执行器的类名
        int hashCode = "fdsfdsf我是谁dsfsd".hashCode();
        System.out.println(hashCode);
    }

    public static void main(String[] args) {
        // 思考：hashCode 算法的乘法基数为什么是 31 ，即 2^31 -1
        int hashCode = "fdsfdsf我是谁dsfsd".hashCode();
        System.out.println(hashCode);
    }
}
