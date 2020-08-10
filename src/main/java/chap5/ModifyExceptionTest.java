package chap5;

import java.util.ArrayList;
import java.util.List;

public class ModifyExceptionTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("dfsafa");
        list.add("23dfsa");
        list.add("besome");

        for (String i : list) {
            /**
             * ConcurrentModificationException 并发修改异常
             * 即使单线程运行，依然会报这个"并发"错误
             *
             * 在迭代时安全修改元素，正确的方法是使用 Iterator
             */
            list.remove(i);
        }

    }
}
