import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SpecialPriceAdjuster {

    public BigDecimal adjustPrice(String itemName, Map<String, List<SalesUnit>> itemsInCart);
    public Map<String, Integer> getItemNamesAndTimesSpecialApplied();

}
