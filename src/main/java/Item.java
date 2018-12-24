import java.math.BigDecimal;

public class Item implements SalesUnit{

    private String name;
    private BigDecimal basePrice;

    public Item(String name, BigDecimal basePrice){
        this.name = name;
        this.basePrice = basePrice;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public BigDecimal getPrice(){
        return this.basePrice;
    }

}
