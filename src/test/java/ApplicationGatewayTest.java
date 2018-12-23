import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApplicationGatewayTest {

    @Test
    public void shouldAddItemsToInventory(){
        Item item1 = new Item(null, null);
        Item item2 = new Item(null, null);
        ArrayList<Item> items = new ArrayList();
        items.add(item1);
        items.add(item2);
        ArrayList<Item> itemsToAdd = items;
        ApplicationGateway gateway = new ApplicationGateway();

        gateway.addItemsToInventory(itemsToAdd);

        List<Item> inventory = gateway.getInventory();
        assertThat(inventory.size(), is(itemsToAdd.size()));
        for(int i = 0; i < inventory.size(); i++){
            assertThat(inventory.get(i), is(itemsToAdd.get(i)));
        }
    }

}
