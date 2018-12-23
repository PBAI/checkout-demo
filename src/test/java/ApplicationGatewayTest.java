import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApplicationGatewayTest {

    @Test
    public void shouldAddItemsToInventory(){
        ApplicationGateway gateway = new ApplicationGateway();
        ArrayList<Item> items = createItemsToAdd();
        gateway.addItemsToInventory(items);

        List<Item> inventory = gateway.getInventory();

        int expectedNumberOfItems = 2;
        assertThat(inventory.size(), is(expectedNumberOfItems));
        for(int i = 0; i < inventory.size(); i++){
            assertThat(inventory.get(i), is(items.get(i)));
        }
    }

    private ArrayList<Item> createItemsToAdd() {
        Item item1 = new Item();
        Item item2 = new Item();
        ArrayList<Item> items = new ArrayList();
        items.add(item1);
        items.add(item2);
        return items;
    }

}
