package chap3;

import java.util.HashSet;
import java.util.Set;

public class ThreeStooges {
    // Set 对象是可变的，但是这里没关系，因为ThreeStooges对象初始化后它就不可变了
    // 这个对象是外部无法改变的，那么从外部角度看，它就是不可变的
    private final Set<String> stooges = new HashSet<>();

    public ThreeStooges() {
        stooges.add("Foo");
        stooges.add("Bar");
        stooges.add("Moe");
    }

    public boolean isStooge(String name) {
        return stooges.contains(name);
    }
}
