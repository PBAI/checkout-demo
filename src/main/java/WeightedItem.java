import java.math.BigDecimal;

public class WeightedItem implements SalesUnit {

    private String name;
    private BigDecimal pricePerPound;
    private BigDecimal weightInPounds;

    public WeightedItem(String name, BigDecimal pricePerPound, BigDecimal weightInPounds){
        this.name = name;
        this.pricePerPound = pricePerPound;
        this.weightInPounds = weightInPounds;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public BigDecimal getPrice(){
        int twoDecimalPlacesPrecision = 2;
        return this.pricePerPound.multiply(this.weightInPounds).setScale(twoDecimalPlacesPrecision);
    }

    public BigDecimal getPricePerPound(){
        return this.pricePerPound;
    }

    public BigDecimal getWeightInPounds(){
        return this.weightInPounds;
    }

}
