package servicetests;

import org.junit.jupiter.api.*;
import requestresult.ClearResult;
import services.Clear;

/** Class to test the Clear service */
public class ClearTest {
    @Test
    public void testClear(){
        Clear service = new Clear();
        ClearResult result = service.clear();
        Assertions.assertEquals("Clear succeeded", result.getMessage());
        Assertions.assertEquals(true, result.isSuccess());
    }
}
