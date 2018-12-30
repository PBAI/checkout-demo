import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class SpecialPriceAdjusterTest {

    @Test
    public void shouldGetCollectionOfItemsOnSpecial(){
        SpecialPriceAdjuster priceAdjuster = new SpecialPriceAdjuster();
        SalesUnit item = new Item("special thing", new BigDecimal("24.50"));
        priceAdjuster.putItemOnSpecial(item.getName(), Special.BUY_ONE_GET_ONE_FREE);
        HashMap<String, Special> expected = new HashMap<>();
        expected.put(item.getName(), Special.BUY_ONE_GET_ONE_FREE);
        assertEquals(expected, priceAdjuster.getItemsOnSpecial());
    }

}
