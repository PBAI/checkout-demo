import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BuyOneGetOneFreeTest {

    @Test
    public void shouldApplyBuyOneGetOneFreeSpecial_RespectingSpecialLimit(){
        String itemName = "item name";
        BigDecimal basePrice = new BigDecimal("10.00");
        SalesUnit item = new Item(itemName, basePrice);
        SpecialPriceAdjuster buyOneGetOneFree = new BuyOneGetOneFree();
        Cart cart = new Cart();

        addItemToCartUpToSpecialLimit(item, cart);
        cart.addItem(item);

        Map<String, List<SalesUnit>> itemsInCart = cart.getItemsInCart();
        BigDecimal firstAppliedPrice = buyOneGetOneFree.adjustPrice(itemName, itemsInCart);
        assertEquals(basePrice, firstAppliedPrice);

        BigDecimal specialPrice = new BigDecimal("0.00");
        BigDecimal secondAppliedPrice = buyOneGetOneFree.adjustPrice(itemName, itemsInCart);
        assertEquals(specialPrice, secondAppliedPrice);

        buyOneGetOneFree.adjustPrice(itemName, itemsInCart);
        buyOneGetOneFree.adjustPrice(itemName, itemsInCart);

        BigDecimal fifthAppliedPrice = buyOneGetOneFree.adjustPrice(itemName, itemsInCart);
        assertEquals(basePrice, fifthAppliedPrice);
    }

    private void addItemToCartUpToSpecialLimit(SalesUnit item, Cart cart) {
        for(int i = 0; i < Special.BUY_ONE_GET_ONE_FREE.getItemLimit(); i++){
            cart.addItem(item);
        }
    }

}
