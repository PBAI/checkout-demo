import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BuyOneGetOneFree implements SpecialPrice {

    @Override
    public BigDecimal apply(String itemName, Map<String, List<SalesUnit>> itemsInCart){
        return null;
    }

}
