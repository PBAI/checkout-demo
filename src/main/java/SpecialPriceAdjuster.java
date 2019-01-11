import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SpecialPriceAdjuster {

    public BigDecimal adjustPrice(SalesUnit item);
}
