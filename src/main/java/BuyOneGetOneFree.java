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
    public BigDecimal adjustPrice(String itemName, Map<String, List<SalesUnit>> itemsInCart){
        BigDecimal basePrice = itemsInCart.get(itemName).get(0).getPrice();
        Special special = Special.BUY_ONE_GET_ONE_FREE;

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
        if(this.itemNamesAndTimesSpecialApplied.get(itemName) % 2 == 0){
            return new BigDecimal("0.00");
        }
        return basePrice;
    }

    private boolean specialPricePreviouslyAppliedForItem(String itemName) {
        return this.itemNamesAndTimesSpecialApplied.containsKey(itemName);
    }

}
