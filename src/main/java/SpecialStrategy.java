public class SpecialStrategy {

    public SpecialPriceAdjuster provideSpecialPriceConcrete(Special special){
        if(special.equals(Special.BUY_ONE_GET_ONE_FREE)){
            return new BuyOneGetOneFree();
        }
        return new NullSpecialPriceAdjuster();
    }

}
