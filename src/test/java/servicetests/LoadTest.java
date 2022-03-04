package servicetests;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import requestresult.LoadRequest;
import requestresult.LoadResult;
import services.Load;

/** Class to test the Load service */
public class LoadTest {
    @Test
    public void testLoad(){
        Load service = new Load();
        User[] users = new User[0];
        Person[] persons = new Person[0];
        Event[] events = new Event[0];
        LoadRequest request = new LoadRequest(users, persons, events);
        LoadResult result = service.load(request);
        Assertions.assertEquals("Successfully added 0 users, 0 persons, and 0 events.", result.getMessage());
        Assertions.assertEquals(true, result.isSuccess());
    }
}
