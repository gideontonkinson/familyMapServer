package servicetests;

import org.junit.jupiter.api.*;
import requestresult.ClearResult;
import requestresult.ResultException;
import services.Clear;

public class ClearTest {
    @Test
    public void testClear(){
        Clear service = new Clear();
        ClearResult result = null;
        try {
            result = service.clear();
        } catch (ResultException e) {
            e.printStackTrace();
        }
        Assertions.assertNotEquals(null, result);
        Assertions.assertEquals("Clear succeeded", result.getMessage());
        Assertions.assertEquals(true, result.isSuccess());
    }
}
