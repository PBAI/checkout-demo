import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ApplicationGatewayTest {

    @Test
    public void shouldConfirmFirstMethod(){
        ApplicationGateway gateway = new ApplicationGateway();
        assertTrue(gateway.firstMethod());
    }

}
