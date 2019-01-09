import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class NullSpecialPriceAdjuster implements SpecialPriceAdjuster {
    @Override
    public BigDecimal adjustPrice(SalesUnit item) {
        return null;
    }

    @Override
    public Map<String, Integer> getItemNamesAndTimesSpecialApplied() {
        return null;
    }
}
