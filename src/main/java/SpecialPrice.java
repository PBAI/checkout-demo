import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SpecialPrice {

    public BigDecimal apply(String itemName, Map<String, List<SalesUnit>> itemsInCart);

}
