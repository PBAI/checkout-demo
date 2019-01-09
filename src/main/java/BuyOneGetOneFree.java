import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyOneGetOneFree implements SpecialPriceAdjuster {

    public Map<String, Integer> itemNamesAndTimesSpecialApplied;

    public BuyOneGetOneFree(){
        this.itemNamesAndTimesSpecialApplied = new HashMap<>();
    }

    @Override
    public BigDecimal adjustPrice(SalesUnit item){
        BigDecimal basePrice = item.getPrice();
        Special special = Special.BUY_ONE_GET_ONE_FREE;
        String itemName = item.getName();

        if(specialPricePreviouslyAppliedForItem(itemName)){
            if(numberOfTimesSpecialAppliedIsUnderLimit(itemName, special)){
                increaseNumberOfTimesSpecialAppliedByOne(itemName);
                return getSpecialPrice(basePrice, itemName);
            } else {
                return basePrice;
            }
        }
        this.itemNamesAndTimesSpecialApplied.put(itemName, 1);
        return getSpecialPrice(basePrice, itemName);
    }

    public Map<String, Integer> getItemNamesAndTimesSpecialApplied(){
        return this.itemNamesAndTimesSpecialApplied;
    }

    private Integer increaseNumberOfTimesSpecialAppliedByOne(String itemName) {
        return this.itemNamesAndTimesSpecialApplied.put(itemName, this.itemNamesAndTimesSpecialApplied.get(itemName) + 1 );
    }

    private boolean numberOfTimesSpecialAppliedIsUnderLimit(String itemName, Special special) {
        return this.itemNamesAndTimesSpecialApplied.get(itemName) < special.getItemLimit();
    }

    private BigDecimal getSpecialPrice(BigDecimal basePrice, String itemName) {
        if(numberOfTimesSpecialAppliedIsEven(itemName)){
            return new BigDecimal("0.00");
        }
        return basePrice;
    }

    private boolean numberOfTimesSpecialAppliedIsEven(String itemName) {
        return this.itemNamesAndTimesSpecialApplied.get(itemName) % 2 == 0;
    }

    private boolean specialPricePreviouslyAppliedForItem(String itemName) {
        return this.itemNamesAndTimesSpecialApplied.containsKey(itemName);
    }

}
