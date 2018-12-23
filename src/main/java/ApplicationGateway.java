import java.util.ArrayList;
import java.util.List;

public class ApplicationGateway {

    private List<Item> inventory;

    public ApplicationGateway(){
        this.inventory = new ArrayList<>();
    }

    public void addItemsToInventory(List<Item> itemsToAdd){
        this.inventory.addAll(itemsToAdd);
    }

    public List<Item> getInventory(){
        return this.inventory;
    }

}
