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
    private Map<String, List<String>> itemsAndPricesScanned;

    public ApplicationGateway() {
        this.inventory = new ArrayList<>();
        this.cart = new Cart();
        this.total = new BigDecimal("0.00");
        this.markdownMap = new HashMap<>();
        this.specialsMap = new HashMap<>();
        this.specialStrategy = new SpecialStrategy();
        this.specialPriceAdjuster = new NullSpecialPriceAdjuster();
        this.itemsAndPricesScanned = new HashMap<>();
    }

    public void addItemNamesToInventory(String...itemNames){
        for(String itemName : itemNames){
            this.inventory.add(itemName);
        }
    }

    public void markdownItemByPercentage(String itemName, Percentage markdownPercentage){
        if((this.inventory.contains(itemName)) &&
                (!itemIsMarkedDown(itemName)) &&
                (!itemOnSpecial(itemName))){
            this.markdownMap.put(itemName, markdownPercentage.getPercentageValueString());
        }
    }

    public void putItemOnSpecial(String itemName, Special specialForItem){
            this.specialsMap.put(itemName, specialForItem);
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
            addSpecialPriceToTotal(itemToScan);
        } else {
            addBasePriceToTotal(itemToScan);
        }
    }

    public void removeOneItemFromTotal(SalesUnit item){
        String itemName = item.getName();
        if(this.itemsAndPricesScanned.containsKey(itemName)){
            List<String> pricesScannedForItem = this.itemsAndPricesScanned.get(itemName);
            subtractPriceOfScannedItemFromTotal(pricesScannedForItem);
            removeLastScannedItemPrice(itemName, pricesScannedForItem);
        }
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

    public Map<String, String> getMarkdownMap(){
        return this.markdownMap;
    }

    public Map<String, Special> getSpecialsMap(){
        return this.specialsMap;
    }

    private void removeLastScannedItemPrice(String itemName, List<String> pricesScannedForItem) {
        pricesScannedForItem.remove(pricesScannedForItem.size() - 1);
        this.itemsAndPricesScanned.replace(itemName, pricesScannedForItem);
    }

    private void subtractPriceOfScannedItemFromTotal(List<String> pricesScannedForItem) {
        this.total = this.total.subtract(
                new BigDecimal(pricesScannedForItem.get(pricesScannedForItem.size() - 1)))
                .setScale(Precision.TWO_DECIMAL_PLACES.getIntegerValue(), BigDecimal.ROUND_UP);
    }

    private void addBasePriceToTotal(SalesUnit itemToScan) {
        BigDecimal priceToAdd = itemToScan.getPrice();
        this.total = this.total.add(priceToAdd)
                .setScale(Precision.TWO_DECIMAL_PLACES.getIntegerValue(), BigDecimal.ROUND_UP);
        addToItemsAndPricesScanned(itemToScan.getName(), priceToAdd);
    }

    private void addSpecialPriceToTotal(SalesUnit itemToScan) {
        String itemName = itemToScan.getName();

        if(currentAdjusterIsAdjusterForItem(itemName)){
            BigDecimal adjustedPrice = this.specialPriceAdjuster.adjustPrice(itemToScan);
            this.total = this.total.add(adjustedPrice)
                    .setScale(Precision.TWO_DECIMAL_PLACES.getIntegerValue(), BigDecimal.ROUND_UP);
            addToItemsAndPricesScanned(itemName, adjustedPrice);
        } else {
            this.specialPriceAdjuster = getCorrectAdjusterForItem(itemName);
            BigDecimal latestAdjusterPrice = this.specialPriceAdjuster.adjustPrice(itemToScan);
            this.total = this.total.add(latestAdjusterPrice)
                    .setScale(Precision.TWO_DECIMAL_PLACES.getIntegerValue(), BigDecimal.ROUND_UP);
            addToItemsAndPricesScanned(itemName, latestAdjusterPrice);
        }
    }

    private boolean currentAdjusterIsAdjusterForItem(String itemName) {
        return this.specialPriceAdjuster.getClass().equals(getCorrectAdjusterForItem(itemName).getClass());
    }

    private boolean itemOnSpecial(String itemName) {
        return this.specialsMap.containsKey(itemName);
    }

    private SpecialPriceAdjuster getCorrectAdjusterForItem(String itemName) {
        return this.specialStrategy.provideSpecialPriceConcrete(this.specialsMap.get(itemName));
    }

    private boolean itemIsMarkedDown(String itemName) {
        return this.markdownMap.containsKey(itemName);
    }

    private void addMarkedDownPriceToTotal(SalesUnit itemToScan) {
        BigDecimal basePrice = itemToScan.getPrice();
        String itemName = itemToScan.getName();
        BigDecimal markdownPercentAsBigDecimal = new BigDecimal(this.markdownMap.get(itemName));
        BigDecimal markedDownAmount = basePrice.multiply(markdownPercentAsBigDecimal);
        BigDecimal priceToAdd = basePrice.subtract(markedDownAmount);
        this.total = this.total.add(priceToAdd).
                setScale(Precision.TWO_DECIMAL_PLACES.getIntegerValue(), BigDecimal.ROUND_UP);
        addToItemsAndPricesScanned(itemName, priceToAdd);
    }

    private void addToItemsAndPricesScanned(String itemName, BigDecimal priceToAdd) {
        if(this.itemsAndPricesScanned.containsKey(itemName)){
            this.itemsAndPricesScanned.get(itemName).add(priceToAdd.toString());
        } else {
            ArrayList<String> pricesScanned = new ArrayList<>();
            pricesScanned.add(priceToAdd.toString());
            this.itemsAndPricesScanned.put(itemName, pricesScanned);
        }
    }

    private boolean itemIsInInventory(String itemName){
        return this.inventory.contains(itemName);
    }

}
