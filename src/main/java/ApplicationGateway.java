import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationGateway {

    private List<String> inventory;
    private Cart cart;
    private BigDecimal total;
    private Map<String, String> markdowns;

    public ApplicationGateway() {
        this.inventory = new ArrayList<>();
        this.cart = new Cart();
        this.total = new BigDecimal("0.00");
        this.markdowns = new HashMap<>();
    }

    public void addItemNamesToInventory(String...itemNames){
        for(String itemName : itemNames){
            this.inventory.add(itemName);
        }
    }

    public void markdownItemByPercentage(String itemName, String markdownPercentage){
        if((this.inventory.contains(itemName)) &&
                (!this.markdowns.containsKey(itemName))){
            this.markdowns.put(itemName, markdownPercentage);
        }
    }

    public void addItemToCart(SalesUnit itemToAdd){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).equals(itemToAdd.getName())){
                this.cart.addItem(itemToAdd);
            }
        }
    }

    public void scanItemToTotal(SalesUnit itemToScan){
        Map<String, List<SalesUnit>> itemsInCart = this.cart.getItemsInCart();
        for(String itemName : itemsInCart.keySet()){
            if(itemName.equals(itemToScan.getName())){
                if(this.markdowns.containsKey(itemName)){
                    addMarkedDownPriceToTotal(itemToScan);
                } else {
                    this.total = this.total.add(itemToScan.getPrice());
                }
            }
        }
    }

    private void addMarkedDownPriceToTotal(SalesUnit itemToScan) {
        BigDecimal basePrice = itemToScan.getPrice();
        BigDecimal markedDownAmount = basePrice.multiply(new BigDecimal(this.markdowns.get(itemToScan.getName())));
        int twoDecimalPlacePrecision = 2;
        this.total = this.total.add(basePrice.subtract(markedDownAmount)).setScale(twoDecimalPlacePrecision);
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

}
