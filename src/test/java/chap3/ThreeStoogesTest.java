package chap3;

import org.junit.jupiter.api.Test;

public class ThreeStoogesTest {

    @Test
    public void testIsStooge() {
        ThreeStooges t = new ThreeStooges();
        System.out.println("he is stooge? " + t.isStooge("he"));
        System.out.println("Moe is stooge? " + t.isStooge("Moe"));
    }
}
