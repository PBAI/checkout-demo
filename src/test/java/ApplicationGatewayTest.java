import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertThat(gateway.getItemsInCart().size(), is(0));

        gateway.addItemToCart(itemWithinInventory);
        assertThat(gateway.getItemsInCart().size(), is(1));
    }

    @Test
    public void shouldMarkdownItemOnlyIfItemWithinInventory(){
        ApplicationGateway gateway = new ApplicationGateway();
        BigDecimal basePrice = new BigDecimal("20.00");
        Item notInStock = new Item("some item", basePrice);

        gateway.markdownItemByPercentage(notInStock.getName(), Percentage.FIFTY_PERCENT.getPercentageValueString());

        assertTrue(gateway.getMarkdownMap().isEmpty());

        Item inStock = new Item("whatever", new BigDecimal("15.00"));
        String inStockName = inStock.getName();
        gateway.addItemNamesToInventory(inStockName);
        gateway.markdownItemByPercentage(inStockName, Percentage.FIFTY_PERCENT.getPercentageValueString());

        assertTrue(gateway.getMarkdownMap().keySet().contains(inStockName));
    }

    @Test
    public void shouldMarkdownItemOnlyIfNotAlreadyOnSpecial(){
        ApplicationGateway gateway = new ApplicationGateway();
        String itemName = "goodie";
        Item item = new Item(itemName, new BigDecimal("5.00"));

        gateway.addItemNamesToInventory(itemName);
        gateway.putItemOnSpecial(itemName, Special.BUY_ONE_GET_ONE_FREE);
        gateway.markdownItemByPercentage(itemName, Percentage.TEN_PERCENT.getPercentageValueString());

        assertTrue(gateway.getMarkdownMap().isEmpty());

        String secondItemName = "toy";
        Item anotherItem = new Item(secondItemName, new BigDecimal("14.00"));
        gateway.addItemNamesToInventory(secondItemName);
        gateway.markdownItemByPercentage(secondItemName, Percentage.TWENTY_FIVE_PERCENT.getPercentageValueString());

        assertTrue(gateway.getMarkdownMap().keySet().contains(secondItemName));
    }

    @Test
    public void shouldMarkdownItemByPercentage_ItemCanOnlyBeMarkedDownOnce(){
        ApplicationGateway gateway = new ApplicationGateway();
        BigDecimal basePrice = new BigDecimal("52.00");
        String itemName = "alreadyMarkedDown";
        Item alreadyMarkedDownItem = new Item(itemName, basePrice);

        gateway.addItemNamesToInventory(itemName);
        gateway.markdownItemByPercentage(itemName, Percentage.TEN_PERCENT.getPercentageValueString());
        gateway.markdownItemByPercentage(itemName, Percentage.TWENTY_FIVE_PERCENT.getPercentageValueString());
        gateway.addItemToCart(alreadyMarkedDownItem);
        gateway.scanItemToTotal(alreadyMarkedDownItem);

        BigDecimal expectedPriceFromFirstMarkDown = new BigDecimal("46.80");
        assertEquals(expectedPriceFromFirstMarkDown, gateway.getTotal());
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

    @Test
    public void shouldPutItemOnSpecial(){
        ApplicationGateway gateway = new ApplicationGateway();
        BigDecimal basePrice = new BigDecimal("9.99");
        Item thing = new Item("thing", basePrice);

        gateway.addItemNamesToInventory(thing.getName());
        gateway.putItemOnSpecial(thing.getName(), Special.BUY_ONE_GET_ONE_FREE);
        gateway.addItemToCart(thing);
        gateway.addItemToCart(thing);

        Map<String, List<SalesUnit>> itemsInCart = gateway.getItemsInCart();
        for(SalesUnit item : itemsInCart.get(thing.getName())){
            gateway.scanItemToTotal(item);
        }

        assertThat(gateway.getTotal(), is(new BigDecimal("9.99")));
    }

}
