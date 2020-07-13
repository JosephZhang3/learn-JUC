package chap4;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将线程安全性委托给线程安全类的车辆追踪器，基于"委托"
 * 尽可能使用final类型的域
 *
 * @author jianghao.zhang
 */
public class DelegatingVehicleTracker {

    private final ConcurrentHashMap<String, Point> locations;// 把"状态"委托给线程安全类 ConcurrentHashMap
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> points) {
        locations = new ConcurrentHashMap<>(points);
        unmodifiableMap = Collections.unmodifiableMap(points);
    }

    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    // 如果需要一个不发生变化的车辆视图，如下浅拷贝 shallow copy（只拷贝引用，不拷贝内容）
//    public Map<String, Point> getLocations(){
//        return Collections.unmodifiableMap(new HashMap<>(locations));
//    }

    public Point getLocation(String id) {
        return locations.get(id);// 不需要copy操作
    }

    /**
     * 更新车辆位置
     *
     * @param id
     * @param x
     * @param y
     */
    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null) {
            // 车辆不存在时，抛出异常
            throw new IllegalArgumentException("Invalid vehicle id: " + id);
        }
    }
}

