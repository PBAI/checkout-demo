import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ItemTest {

    @Test
    public void shouldSetItemNameAndRegularPriceOnInstantiation(){
        String expectedName = "milk";
        Item milk = new Item(expectedName, new BigDecimal("2.00"));
        assertThat(milk.getName(), is(expectedName));
    }

}
