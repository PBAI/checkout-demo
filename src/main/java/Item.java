import java.math.BigDecimal;

public class Item {

    private String name;
    private BigDecimal regularPrice;

    public Item(String name, BigDecimal regularPrice){
        this.name = name;
        this.regularPrice = regularPrice;
    }

    public String getName(){
        return this.name;
    }

    public BigDecimal getRegularPrice(){
        return this.regularPrice;
    }

}
