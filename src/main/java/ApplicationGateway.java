import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplicationGateway {

    private List<String> inventory;
    private Cart cart;

    public ApplicationGateway() {
        this.inventory = new ArrayList<>();
        this.cart = new Cart();
    }

    public void addItemsToInventory(List<String> itemsToAdd){
        this.inventory.addAll(itemsToAdd);
    }

    public void addItemToCart(Item itemToAdd){
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).equals(itemToAdd.getName())){
                this.cart.addItem(itemToAdd);
            }
        }
    }

    public Map<String, List<Item>> getDistinctItemsInCart(){
        return this.cart.getItemsInCart();
    }

    public List<String> getInventory(){
        return this.inventory;
    }

}
