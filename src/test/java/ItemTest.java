import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ItemTest {

    @Test
    public void shouldSetItemNameAndBasePriceOnInstantiation(){
        String expectedName = "milk";
        BigDecimal expectedBasePrice = new BigDecimal("2.00");
        Item milk = new Item(expectedName, expectedBasePrice);
        assertThat(milk.getName(), is(expectedName));
        assertThat(milk.getPrice(), is(expectedBasePrice));
    }

}
