import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ItemTest {

    @Test
    public void shouldSetItemNameAndRegularPriceOnInstantiation(){
        String expectedName = "milk";
        BigDecimal expectedRegularPrice = new BigDecimal("2.00");
        Item milk = new Item(expectedName, expectedRegularPrice);
        assertThat(milk.getName(), is(expectedName));
        assertThat(milk.getRegularPrice(), is(expectedRegularPrice));
    }

}
