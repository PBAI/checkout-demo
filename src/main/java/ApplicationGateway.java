import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplicationGateway {

    private List<Item> inventory;

    public ApplicationGateway(){
        this.inventory = new ArrayList<>();
    }

    public void addItemsToInventory(List<Item> itemsToAdd){
        this.inventory.addAll(itemsToAdd);
    }

    public void addItemToCart(Item itemToAdd){

    }

    public Map<String, List<Item>> getDistinctItemsInCart(){
        return null;
    }

    public List<Item> getInventory(){
        return this.inventory;
    }

}
