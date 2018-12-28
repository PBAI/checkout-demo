import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ApplicationGatewayTest {

    @Test
    public void shouldAddItemNamesToInventory() {
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

    @Test
    public void shouldMarkdownItemByPercentage(){
        ApplicationGateway gateway = new ApplicationGateway();
        BigDecimal basePrice = new BigDecimal("52.00");
        String itemName = "something";
        Item markedDownItem = new Item(itemName, basePrice);

        gateway.addItemNamesToInventory(itemName);
        String markdownPercentage = Percentage.TWENTY_FIVE_PERCENT.getPercentageValueString();
        gateway.markdownItemByPercentage(itemName, markdownPercentage);
        gateway.addItemToCart(markedDownItem);
        gateway.scanItemToTotal(markedDownItem);

        BigDecimal markedDownPrice = new BigDecimal("39.00");
        assertEquals(markedDownPrice, gateway.getTotal());
    }

    @Test
    public void shouldAddMarkdownPriceToTotal(){
        ApplicationGateway gateway = new ApplicationGateway();
        String item1Name = "thingOne";
        BigDecimal item1BasePrice = new BigDecimal("20.00");
        Item item1 = new Item(item1Name, item1BasePrice);
        String item2Name = "cheaper";
        BigDecimal item2BasePrice = new BigDecimal("10.00");
        Item item2 = new Item(item2Name, item2BasePrice);

        gateway.addItemNamesToInventory(item1Name, item2Name);
        gateway.addItemToCart(item1);
        gateway.addItemToCart(item2);
        gateway.markdownItemByPercentage(item2Name, Percentage.FIFTY_PERCENT.getPercentageValueString());
        gateway.scanItemToTotal(item1);
        gateway.scanItemToTotal(item2);

        BigDecimal expectedTotal = new BigDecimal("25.00");
        assertThat(gateway.getTotal(), is(expectedTotal));
    }


}
