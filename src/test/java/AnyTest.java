import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class AnyTest {

    @Test
    public void gg() {
       String d = new BigDecimal(String.valueOf(78.43243)).divide(new BigDecimal(10000)).toString();
        System.out.println(d);
    }
}
