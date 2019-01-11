import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ThreeForFiveTest {

    @Test
    public void shouldReturnThreeForFiveSpecialPrice_RespectingLimit(){
        SpecialPriceAdjuster adjuster = new ThreeForFive();
        BigDecimal basePrice = new BigDecimal("2.00");
        Item item = new Item("household good", basePrice);
        Cart cart = new Cart();
        addItemsToCartUpToSpecialLimit(item, cart);
        cart.addItem(item);

        BigDecimal specialPrice = new BigDecimal("1.66");
        int numberOfItemsInCartWithinSpecialLimit = cart.getItemsInCart().get(item.getName()).size() - 1;
        for(int i = 0; i < numberOfItemsInCartWithinSpecialLimit; i++){
            BigDecimal actualReturnPrice = adjuster.adjustPrice(item);
            assertThat(actualReturnPrice, is(specialPrice));
        }
        BigDecimal actualReturnPrice = adjuster.adjustPrice(item);
        assertThat(actualReturnPrice, is(basePrice));
    }

    private void addItemsToCartUpToSpecialLimit(Item item, Cart cart) {
        for(int i = 0; i < Special.THREE_FOR_FIVE.getItemLimit(); i++){
            cart.addItem(item);
        }
    }

}
