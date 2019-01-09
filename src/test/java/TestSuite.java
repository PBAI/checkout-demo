import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ApplicationGatewayTest.class,
        CartTest.class,
        ItemTest.class,
        WeightedItemTest.class,
        BuyOneGetOneFreeTest.class
})

public class TestSuite {
}