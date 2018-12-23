import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApplicationGatewayTest {

    @Test
    public void shouldAddItemsToInventory() {
        Item item1 = new Item(null, null);
        Item item2 = new Item(null, null);
        ArrayList<String> itemNames = new ArrayList();
        itemNames.add(item1.getName());
        itemNames.add(item2.getName());
        ApplicationGateway gateway = new ApplicationGateway();

        gateway.addItemsToInventory(itemNames);

        List<String> inventory = gateway.getInventory();
        assertThat(inventory.size(), is(itemNames.size()));
        for(int i = 0; i < inventory.size(); i++){
            assertThat(inventory.get(i), is(itemNames.get(i)));
        }
    }

    @Test
    public void shouldOnlyAddItemToCartIfFoundWithinInventory() {
        ApplicationGateway gateway = new ApplicationGateway();
        Item itemWithinInventory = new Item("soap", new BigDecimal(".99"));
        ArrayList itemsToAdd = new ArrayList();
        itemsToAdd.add(itemWithinInventory.getName());
        gateway.addItemsToInventory(itemsToAdd);

        Item itemNotFoundWithinInventory = new Item("lotion", new BigDecimal("3.25"));
        gateway.addItemToCart(itemNotFoundWithinInventory);
        assertThat(gateway.getDistinctItemsInCart().size(), is(0));

        gateway.addItemToCart(itemWithinInventory);
        assertThat(gateway.getDistinctItemsInCart().size(), is(1));
    }

}
