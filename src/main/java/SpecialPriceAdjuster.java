import java.util.HashMap;
import java.util.Map;

public class SpecialPriceAdjuster {

    private Map<String, Special> itemsOnSpecial;

    public SpecialPriceAdjuster(){
        this.itemsOnSpecial = new HashMap<>();
    }

    public void putItemOnSpecial(String itemName, Special specialForItem){
        this.itemsOnSpecial.put(itemName, specialForItem);
    }

    public Map<String, Special> getItemsOnSpecial(){
        return this.itemsOnSpecial;
    }

}
