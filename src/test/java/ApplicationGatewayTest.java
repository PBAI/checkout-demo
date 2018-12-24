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
        String itemOneName = "thingOne";
        String itemTwoName = "thingTwo";
        ApplicationGateway gateway = new ApplicationGateway();

        gateway.addItemNamesToInventory(itemOneName, itemTwoName);

        List<String> inventory = gateway.getInventory();

        int expectedNumberOfNames = 2;
        assertThat(inventory.size(), is(expectedNumberOfNames));
        assertThat(inventory.get(0), is(itemOneName));
        assertThat(inventory.get(1), is(itemTwoName));
    }

    @Test
    public void shouldOnlyAddItemToCartIfFoundWithinInventory() {
        ApplicationGateway gateway = new ApplicationGateway();
        SalesUnit itemWithinInventory = new Item("soap", new BigDecimal(".99"));
        gateway.addItemNamesToInventory(itemWithinInventory.getName());

        SalesUnit itemNotFoundWithinInventory = new Item("lotion", new BigDecimal("3.25"));
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

        BigDecimal bananaPrice = new BigDecimal(".99");
        BigDecimal applePrice = new BigDecimal(".50");
        BigDecimal grapesPricePerPound = new BigDecimal("2.00");
        BigDecimal grapesWeightInPounds = new BigDecimal("2.00");

        SalesUnit banana = new Item("banana", bananaPrice);
        SalesUnit apple = new Item("apple", applePrice);
        SalesUnit grapes = new WeightedItem(
                "grapes",
                grapesPricePerPound,
                grapesWeightInPounds);

        gateway.addItemNamesToInventory(
                banana.getName(),
                apple.getName(),
                grapes.getName());

        gateway.addItemToCart(banana);
        gateway.addItemToCart(apple);
        gateway.addItemToCart(grapes);

        gateway.scanItemToTotal(banana);
        gateway.scanItemToTotal(apple);
        gateway.scanItemToTotal(grapes);
        SalesUnit itemNotInCart = new Item("shoes", new BigDecimal("10.59"));
        gateway.scanItemToTotal(itemNotInCart);

        BigDecimal expectedTotal = new BigDecimal("5.49");
        assertEquals(expectedTotal, gateway.getTotal());
    }

}
