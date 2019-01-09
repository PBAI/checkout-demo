import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpecialStrategyTest {

    @Test
    public void shouldInstantiateSpecialPriceConcreteAccordingToSpecial(){
        SpecialStrategy strategy = new SpecialStrategy();
        SpecialPriceAdjuster concrete = strategy.provideSpecialPriceConcrete(Special.BUY_ONE_GET_ONE_FREE);
        assertThat(concrete.getClass().toString(), is(BuyOneGetOneFree.class.toString()));
    }

}
