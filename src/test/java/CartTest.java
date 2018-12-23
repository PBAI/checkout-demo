import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CartTest {

    @Test
    public void shouldAddItemToCart(){
        Cart cart = new Cart();
        assertThat(cart.getItems().size(), is(0));

        Item bread = new Item("bread", new BigDecimal("1.50"));
        cart.addItem(bread);
        Map<String, List<Item>> items = cart.getItems();
        assertThat(items.size(), is(1));
        assertThat(items.get(0), is(bread));

        Item cheese = new Item("cheese", new BigDecimal("3.25"));
        cart.addItem(cheese);
        assertThat(items.size(), is(2));
        assertThat(items.get(1), is(cheese));
    }

}
