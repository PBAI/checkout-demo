import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationGateway {

    private List<String> inventory;
    private Cart cart;
    private BigDecimal total;
    private Map<String, String> markdownMap;
    private Map<String, Special> specialsMap;
    private SpecialStrategy specialStrategy;
    private SpecialPriceAdjuster specialPriceAdjuster;

    public ApplicationGateway() {
        this.inventory = new ArrayList<>();
        this.cart = new Cart();
        this.total = new BigDecimal("0.00");
        this.markdownMap = new HashMap<>();
        this.specialsMap = new HashMap<>();
        this.specialStrategy = new SpecialStrategy();
        this.specialPriceAdjuster = new NullSpecialPriceAdjuster();
    }

    public void addItemNamesToInventory(String...itemNames){
        for(String itemName : itemNames){
            this.inventory.add(itemName);
        }
    }

    public void markdownItemByPercentage(String itemName, String markdownPercentage){
        if((this.inventory.contains(itemName)) &&
                (!itemIsMarkedDown(itemName))){
            this.markdownMap.put(itemName, markdownPercentage);
        }
    }

    public void putItemOnSpecial(String itemName, Special specialForItem){
        if(!itemIsMarkedDown(itemName)){
            this.specialsMap.put(itemName, specialForItem);
        }
    }

    public void addItemToCart(SalesUnit itemToAdd){
        if(itemIsInInventory(itemToAdd.getName())){
            this.cart.addItem(itemToAdd);
        }
    }

    public void scanItemToTotal(SalesUnit itemToScan){
        String itemName = itemToScan.getName();
        if(itemIsMarkedDown(itemName)){
            addMarkedDownPriceToTotal(itemToScan);
        } else if(itemOnSpecial(itemName)){
            if(currentAdjusterIsAdjusterForItem(itemName)){
                this.total = this.total.add(this.specialPriceAdjuster.adjustPrice(itemToScan))
                        .setScale(Precision.TWO_DECIMAL_PLACES.getIntegerValue(), BigDecimal.ROUND_DOWN);
            } else {
                this.specialPriceAdjuster = getCorrectAdjusterForItem(itemName);
                this.total = this.total.add(this.specialPriceAdjuster.adjustPrice(itemToScan))
                        .setScale(Precision.TWO_DECIMAL_PLACES.getIntegerValue(), BigDecimal.ROUND_DOWN);
            }
        } else {
            this.total = this.total.add(itemToScan.getPrice())
                    .setScale(Precision.TWO_DECIMAL_PLACES.getIntegerValue(), BigDecimal.ROUND_DOWN);
        }
    }

    private boolean currentAdjusterIsAdjusterForItem(String itemName) {
        return this.specialPriceAdjuster.equals(getCorrectAdjusterForItem(itemName));
    }

    private boolean itemOnSpecial(String itemName) {
        return this.specialsMap.containsKey(itemName);
    }

    private SpecialPriceAdjuster getCorrectAdjusterForItem(String itemName) {
        return this.specialStrategy.provideSpecialPriceConcrete(this.specialsMap.get(itemName));
    }

    public BigDecimal getTotal(){
        return this.total;
    }

    public Map<String, List<SalesUnit>> getItemsInCart(){
        return this.cart.getItemsInCart();
    }

    public List<String> getInventory(){
        return this.inventory;
    }

    private boolean itemIsMarkedDown(String itemName) {
        return this.markdownMap.containsKey(itemName);
    }

    private void addMarkedDownPriceToTotal(SalesUnit itemToScan) {
        BigDecimal basePrice = itemToScan.getPrice();
        BigDecimal markdownPercentAsBigDecimal = new BigDecimal(this.markdownMap.get(itemToScan.getName()));
        BigDecimal markedDownAmount = basePrice.multiply(markdownPercentAsBigDecimal);
        this.total = this.total.add(basePrice.subtract(markedDownAmount)).
                setScale(Precision.TWO_DECIMAL_PLACES.getIntegerValue(), BigDecimal.ROUND_DOWN);
    }

    private boolean itemIsInInventory(String itemName){
        return this.inventory.contains(itemName);
    }

}
