package servicetests;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import requestresult.LoadRequest;
import requestresult.LoadResult;
import requestresult.ResultException;
import services.Load;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class LoadTest {
    @Test
    public void testLoad(){
        Load service = new Load();
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Person> persons = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();
        LoadRequest request = new LoadRequest(users, persons, events);
        LoadResult result = null;
        try {
            result = service.load(request);
        } catch (ResultException e) {
            e.printStackTrace();
        }
        Assertions.assertNotEquals(null, result);
        Assertions.assertEquals("Successfully added 0 users, 0 persons, 0 events.", result.getMessage());
        Assertions.assertEquals(true, result.isSuccess());
    }
}
