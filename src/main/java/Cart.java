import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private Map<String, List<SalesUnit>> itemsInCart;

    public Cart(){
        this.itemsInCart = new HashMap<>();
    }

    public void addItem(SalesUnit itemToAdd){
        String itemName = itemToAdd.getName();
        if(this.itemsInCart.containsKey(itemName)){
            this.itemsInCart.get(itemName).add(itemToAdd);
        } else {
            ArrayList<SalesUnit> valueList = new ArrayList<>();
            valueList.add(itemToAdd);
            this.itemsInCart.put(itemName, valueList);
        }
    }

    public Map<String, List<SalesUnit>> getItemsInCart(){
        return this.itemsInCart;
    }

}
