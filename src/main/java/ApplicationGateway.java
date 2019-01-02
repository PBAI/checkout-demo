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

    public ApplicationGateway() {
        this.inventory = new ArrayList<>();
        this.cart = new Cart();
        this.total = new BigDecimal("0.00");
        this.markdownMap = new HashMap<>();
        this.specialsMap = new HashMap<>();
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
        for(int i = 0; i < inventory.size(); i++){
            if(itemToScanIsInCart(itemToAdd, inventory.get(i))){
                this.cart.addItem(itemToAdd);
            }
        }
    }

    public void scanItemToTotal(SalesUnit itemToScan){
        Map<String, List<SalesUnit>> itemsInCart = this.cart.getItemsInCart();
        for(String itemName : itemsInCart.keySet()){
            if(itemToScanIsInCart(itemToScan, itemName)){
                if(itemIsMarkedDown(itemName)){
                    addMarkedDownPriceToTotal(itemToScan);
                } else {
                    this.total = this.total.add(itemToScan.getPrice());
                }
            }
        }
    }

    public BigDecimal getTotal(){
        return this.total;
    }

    public Map<String, List<SalesUnit>> getDistinctItemsInCart(){
        return this.cart.getItemsInCart();
    }

    public List<String> getInventory(){
        return this.inventory;
    }

    private boolean itemIsMarkedDown(String itemName) {
        return this.markdownMap.containsKey(itemName);
    }

    private boolean itemToScanIsInCart(SalesUnit itemToScan, String itemInCartName) {
        return itemInCartName.equals(itemToScan.getName());
    }

    private void addMarkedDownPriceToTotal(SalesUnit itemToScan) {
        BigDecimal basePrice = itemToScan.getPrice();
        BigDecimal markdownPercentAsBigDecimal = new BigDecimal(this.markdownMap.get(itemToScan.getName()));
        BigDecimal markedDownAmount = basePrice.multiply(markdownPercentAsBigDecimal);
        int twoDecimalPlacePrecision = 2;
        this.total = this.total.add(basePrice.subtract(markedDownAmount)).setScale(twoDecimalPlacePrecision);
    }

}
