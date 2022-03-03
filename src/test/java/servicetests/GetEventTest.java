package servicetests;

import dataaccess.DaoException;
import dataaccess.Database;
import dataaccess.EventDao;
import model.AuthToken;
import model.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.*;
import services.GetEvent;

public class GetEventTest {
    private static Database db;
    private static EventDao eventDao;

    @BeforeAll
    public static void createData() throws DaoException{
        db = new Database();
        db.openConnection();
        db.closeConnection(true);
    }

    @BeforeEach
    public void setUp() throws DaoException {
        db.openConnection();
        db.createTablesForTesting();
        eventDao = new EventDao(db.getConnection());
        Event event1 = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        Event event2 = new Event("1255", "gmt27", "1245",11,23,"France", "Paris", "Death", 2000);
        Event event3 = new Event("1256", "gmt27", "1246",11,23,"France", "Paris", "Death", 2000);
        eventDao.addToDB(event1);
        eventDao.addToDB(event2);
        eventDao.addToDB(event3);
        db.closeConnection(true);
    }

    @Test
    public void getEventTest(){
        GetEvent service = new GetEvent();
        AuthToken authToken = new AuthToken("1248", "gmt27");
        GetEventResult result = service.getEvent("1254", authToken);
        Assertions.assertEquals("1254", result.getEventID());
        Assertions.assertEquals(true, result.isSuccess());
    }

    @Test
    public void getEventTestDNE(){
        GetEvent service = new GetEvent();
        AuthToken authToken = new AuthToken("1248", "gmt28");
        GetEventResult result = service.getEvent("1253", authToken);
        Assertions.assertEquals("Error: No event with that ID", result.getMessage());
        Assertions.assertEquals(false, result.isSuccess());
    }

    @Test
    public void getEventsTest(){
        GetEvent service = new GetEvent();
        AuthToken authToken = new AuthToken("1248", "gmt27");
        GetEventsResult result = service.getEvents(authToken);
        Assertions.assertEquals(3, result.getData().size());
        Assertions.assertEquals(true, result.isSuccess());
    }

    @Test
    public void getEventsTestDNE(){
        GetEvent service = new GetEvent();
        AuthToken authToken = new AuthToken("1248", "gmt28");
        GetEventsResult result = service.getEvents(authToken);
        Assertions.assertEquals("Error: No Events for that User", result.getMessage());
        Assertions.assertEquals(false, result.isSuccess());
    }
}
