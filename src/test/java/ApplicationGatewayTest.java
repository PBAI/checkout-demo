import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ApplicationGatewayTest {

    @Test
    public void shouldAddItemsToInventory() {
        Item item1 = new Item(null, null);
        Item item2 = new Item(null, null);
        ArrayList<String> itemNames = new ArrayList();
        itemNames.add(item1.getName());
        itemNames.add(item2.getName());
        ApplicationGateway gateway = new ApplicationGateway();

        gateway.addItemNamesToInventory(itemNames);

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
        gateway.addItemNamesToInventory(itemsToAdd);

        Item itemNotFoundWithinInventory = new Item("lotion", new BigDecimal("3.25"));
        gateway.addItemToCart(itemNotFoundWithinInventory);
        assertThat(gateway.getDistinctItemsInCart().size(), is(0));

        gateway.addItemToCart(itemWithinInventory);
        assertThat(gateway.getDistinctItemsInCart().size(), is(1));
    }

    @Test
    public void scanningItemUpdatesTotal_OnlyIfItemWithinCart(){
        ApplicationGateway gateway = new ApplicationGateway();
        BigDecimal expectedInitialTotal = new BigDecimal("0.00");
        assertThat(gateway.getTotal(), is(expectedInitialTotal));

        SalesUnit banana = new Item("banana", new BigDecimal(".99"));
        SalesUnit apple = new Item("apple", new BigDecimal(".50"));
        SalesUnit grapes = new WeightedItem(
                "grapes",
                new BigDecimal("2.00"),
                new BigDecimal("2.00")
        );
        ArrayList itemNamesForInventory = new ArrayList();
        itemNamesForInventory.add(banana.getName());
        itemNamesForInventory.add(apple.getName());
        itemNamesForInventory.add(grapes.getName());
        gateway.addItemNamesToInventory(itemNamesForInventory);

        gateway.addItemToCart(banana);
        gateway.addItemToCart(apple);
        gateway.addItemToCart(grapes);

        gateway.scanItemToTotal(banana);
        gateway.scanItemToTotal(apple);
        gateway.scanItemToTotal(grapes);
        Item itemNotInCart = new Item("shoes", new BigDecimal("10.59"));
        gateway.scanItemToTotal(itemNotInCart);

        BigDecimal expectedTotal = new BigDecimal("5.49");
        assertEquals(expectedTotal, gateway.getTotal());
    }

}
