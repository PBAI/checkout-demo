import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class NullSpecialPriceAdjuster implements SpecialPriceAdjuster {
    @Override
    public BigDecimal adjustPrice(String itemName, Map<String, List<SalesUnit>> itemsInCart) {
        return null;
    }

    @Override
    public Map<String, Integer> getItemNamesAndTimesSpecialApplied() {
        return null;
    }
}
