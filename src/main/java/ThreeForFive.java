import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ThreeForFive implements SpecialPriceAdjuster {

    private Map<String, Integer> itemAndNumberOfTimesSpecialApplied;

    public ThreeForFive(){
        this.itemAndNumberOfTimesSpecialApplied = new HashMap<>();
    }

    @Override
    public BigDecimal adjustPrice(SalesUnit item) {
        String itemName = item.getName();
        BigDecimal specialPrice = new BigDecimal("1.66");
        if(specialPreviouslyAppliedToItem(itemName)){
            if(numberOfTimesSpecialAppliedIsUnderLimit(itemName)){
                increaseNumberOfTimesSpecialAppliedByOne(itemName);
                return specialPrice;
            } else {
                return item.getPrice();
            }
        }
        this.itemAndNumberOfTimesSpecialApplied.put(itemName, 1);
        return specialPrice;
    }

    private void increaseNumberOfTimesSpecialAppliedByOne(String itemName) {
        this.itemAndNumberOfTimesSpecialApplied.put(itemName,
                this.itemAndNumberOfTimesSpecialApplied.get(itemName) + 1);
    }

    private boolean numberOfTimesSpecialAppliedIsUnderLimit(String itemName) {
        return this.itemAndNumberOfTimesSpecialApplied.get(itemName) < Special.THREE_FOR_FIVE.getItemLimit();
    }

    private boolean specialPreviouslyAppliedToItem(String itemName) {
        return this.itemAndNumberOfTimesSpecialApplied.containsKey(itemName);
    }

}
