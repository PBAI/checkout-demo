import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertSame;

public class CartTest {

    @Test
    public void shouldAddItemToCart(){
        Cart cart = new Cart();
        assertThat(cart.getItemsInCart().size(), is(0));

        SalesUnit bread = new Item("bread", new BigDecimal("1.50"));
        cart.addItem(bread);
        Map<String, List<SalesUnit>> items = cart.getItemsInCart();
        assertThat(items.size(), is(1));
        assertSame(items.get(bread.getName()).get(0), bread);

        SalesUnit cheese = new Item("cheese", new BigDecimal("3.25"));
        cart.addItem(cheese);
        assertThat(items.size(), is(2));
        assertThat(items.get(cheese.getName()).get(0), is(cheese));
    }

}
