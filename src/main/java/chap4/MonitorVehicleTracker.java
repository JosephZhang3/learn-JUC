package chap4;


import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于 Java监视器模式 实现的"车辆追踪器"
 *
 * @author jianghao.zhang
 */
@ThreadSafe
public class MonitorVehicleTracker {

    @GuardedBy("this field")
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint loc = locations.get(id);
        return loc == null ? null : new MutablePoint(loc);// TODO 为什么不直接返回，而是先复制数据？
    }

    // set方法目的是修改位置，注意不是add（初始化）
    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint loc = locations.get(id);
        if (loc == null) {
            throw new IllegalArgumentException("No such ID: " + id);
        }
        loc.x = x;
        loc.y = y;
    }

    public static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
        Map<String, MutablePoint> result = new HashMap<>();
        for (String id : m.keySet()) {
            // 其实深度拷贝还是借助了构造函数，这样就不是复制Point对象的引用，而是复制真正的内容（field值）
            result.put(id, new MutablePoint(m.get(id)));
        }
        return Collections.unmodifiableMap(result);
        // 转变成一个不可能被外部代码修改的map对象，任何modify操作都会触发异常
        //
        //        public V put(K key, V value) {
        //            throw new UnsupportedOperationException();
        //        }
        //        public V remove(Object key) {
        //            throw new UnsupportedOperationException();
        //        }
        //        public void putAll(Map<? extends K, ? extends V> m) {
        //            throw new UnsupportedOperationException();
        //        }
        //        public void clear() {
        //            throw new UnsupportedOperationException();
        //        }
    }
}

// 可变的点坐标，不是线程安全的
@NotThreadSafe
class MutablePoint {
    public int x, y;// 特别注意，这里修饰符是 public ！因此这个类的对象是可被修改的

    public MutablePoint() {
        x = 0;
        y = 0;
    }

    public MutablePoint(MutablePoint p) {
        this.x = p.x;
        this.y = p.y;
    }
}