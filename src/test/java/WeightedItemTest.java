import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class WeightedItemTest {

    @Test
    public void shouldSetName_PricePerPound_AndWeightInPoundsOnConstruction(){
        String expectedName = "thing";
        BigDecimal expectedPricePerPound = new BigDecimal("2.75");
        BigDecimal expectedWeightInPounds = new BigDecimal("4.00");
        WeightedItem weightedItem = new WeightedItem(expectedName, expectedPricePerPound, expectedWeightInPounds);

        assertThat(weightedItem.getName(), is(expectedName));
        assertThat(weightedItem.getPricePerPound(), is(expectedPricePerPound));
        assertThat(weightedItem.getWeightInPounds(), is(expectedWeightInPounds));
    }

    @Test
    public void shouldGetPriceAccordingToWeightInPounds(){
        BigDecimal expectedPricePerPound = new BigDecimal("2.50");
        BigDecimal expectedWeightInPounds = new BigDecimal("3.00");
        WeightedItem apples = new WeightedItem("apples", expectedPricePerPound, expectedWeightInPounds);

        int twoDecimalPlacePrecision = 2;
        BigDecimal expectedPrice = new BigDecimal("7.50").setScale(twoDecimalPlacePrecision);
        assertThat(apples.getPrice(), is(expectedPrice));
    }

}
