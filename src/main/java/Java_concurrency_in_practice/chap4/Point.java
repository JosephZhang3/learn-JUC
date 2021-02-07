package Java_concurrency_in_practice.chap4;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;

@Immutable
@ThreadSafe
public class Point{
    public final int x, y;// 特别注意，这里是 final ，如果你想要通过 set()方法改变这个field的值，就会报错 Cannot assign a value to final variable 'x'

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

}
