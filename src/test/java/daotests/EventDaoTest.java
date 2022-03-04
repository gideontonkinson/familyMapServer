package daotests;


import dataaccess.DaoException;
import dataaccess.Database;
import dataaccess.EventDao;
import model.Event;
import org.junit.jupiter.api.*;

/** Class to test the EventDao */
public class EventDaoTest {
    private static Database db;
    private static EventDao eventDao;

    @BeforeAll
    public static void createDatabase() throws DaoException {
        db = new Database();
        db.openConnection();
        db.closeConnection(true);
    }

    @BeforeEach
    public void setUp() throws DaoException {
        db.openConnection();
        db.createTablesForTesting();
    }

    @AfterEach
    public void tearDown() throws DaoException {
        db.closeConnection(false);
    }

    @AfterAll
    static void tearDownAll() throws DaoException{
        db.openConnection();
        db.createTablesForTesting();
        db.closeConnection(true);
    }

    @Test
    public void testAddEventToDB() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        Assertions.assertEquals(true, eventDao.addToDB(event));
    }

    @Test
    public void testAddSameEventToDB() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        eventDao.addToDB(event);
        Assertions.assertThrows(DaoException.class, () -> eventDao.addToDB(event));
    }

    @Test
    public void testGetFromDB() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        eventDao.addToDB(event);
        Assertions.assertEquals(event.getEventID(), eventDao.getFromDB("1254").getEventID());
    }

    @Test
    public void testGetFromDBDNE() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Assertions.assertEquals(null, eventDao.getFromDB("1254"));
    }

    @Test
    public void testGetEventsForPerson() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event1 = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        Event event2 = new Event("1255", "gmt27", "1245",11,23,"France", "Paris", "Death", 2000);
        Event event3 = new Event("1256", "gmt27", "1246",11,23,"France", "Paris", "Death", 2000);
        eventDao.addToDB(event1);
        eventDao.addToDB(event2);
        eventDao.addToDB(event3);
        Assertions.assertEquals(2, eventDao.getEventsForPerson("1245").size());
    }

    @Test
    public void testGetEventsForPersonDNE() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Assertions.assertEquals(null, eventDao.getEventsForPerson("1245"));
    }

    @Test
    public void testGetEventsForUser() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event1 = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        Event event2 = new Event("1255", "gmt27", "1246",11,23,"France", "Paris", "Death", 2000);
        Event event3 = new Event("1256", "gmt28", "1246",11,23,"France", "Paris", "Death", 2000);
        eventDao.addToDB(event1);
        eventDao.addToDB(event2);
        eventDao.addToDB(event3);
        Assertions.assertEquals(2, eventDao.getEventsForUser("gmt27").size());
    }

    @Test
    public void testGetEventsForUserDNE() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Assertions.assertEquals(null, eventDao.getEventsForUser("gmt27"));
    }

    @Test
    public void testClearEventsForUser() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event1 = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        Event event2 = new Event("1255", "gmt27", "1246",11,23,"France", "Paris", "Death", 2000);
        Event event3 = new Event("1256", "gmt28", "1246",11,23,"France", "Paris", "Death", 2000);
        eventDao.addToDB(event1);
        eventDao.addToDB(event2);
        eventDao.addToDB(event3);
        eventDao.clearEventsForUser("gmt27");
        Assertions.assertEquals(null, eventDao.getEventsForUser("gmt27"));
        Assertions.assertEquals(1, eventDao.getEventsForUser("gmt28").size());
    }

    @Test
    public void testUpdateUsername() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event1 = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        Event event2 = new Event("1255", "gmt27", "1246",11,23,"France", "Paris", "Death", 2000);
        Event event3 = new Event("1256", "jmt28", "1246",11,23,"France", "Paris", "Death", 2000);
        eventDao.addToDB(event1);
        eventDao.addToDB(event2);
        eventDao.addToDB(event3);
        Assertions.assertEquals(true, eventDao.updateUsername("gmt27", "gmt28"));
        Assertions.assertEquals(2, eventDao.getEventsForUser("gmt28").size());
    }

    @Test
    public void testUsernameDNE() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Assertions.assertEquals(false, eventDao.updateUsername("gmt27","gmt28"));
    }

    @Test
    public void testUpdateLongitude() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        eventDao.addToDB(event);
        Assertions.assertEquals(true, eventDao.updateLongitude("1254",28));
        Assertions.assertEquals(28, eventDao.getFromDB("1254").getLongitude());
    }

    @Test
    public void testUpdateLongitudeDNE() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Assertions.assertEquals(false, eventDao.updateLongitude("1254",28));
    }

    @Test
    public void testUpdateLatitude() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        eventDao.addToDB(event);
        Assertions.assertEquals(true,eventDao.updateLatitude("1254",13));
        Assertions.assertEquals(13, eventDao.getFromDB("1254").getLatitude());
    }

    @Test
    public void testUpdateLatitudeDNE() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Assertions.assertEquals(false, eventDao.updateLatitude("1254",13));
    }

    @Test
    public void testUpdateCountry() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        eventDao.addToDB(event);
        Assertions.assertEquals(true,eventDao.updateCountry("1254","England"));
        Assertions.assertEquals("England", eventDao.getFromDB("1254").getCountry());
    }

    @Test
    public void testUpdateCountryDNE() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Assertions.assertEquals(false, eventDao.updateCountry("1254","England"));
    }

    @Test
    public void testUpdateCity() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        eventDao.addToDB(event);
        Assertions.assertEquals(true, eventDao.updateCity("1254", "London"));
        Assertions.assertEquals("London",eventDao.getFromDB("1254").getCity());
    }

    @Test
    public void testUpdateCityDNE() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Assertions.assertEquals(false, eventDao.updateCity("1254","London"));
    }

    @Test
    public void testUpdateYear() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        eventDao.addToDB(event);
        Assertions.assertEquals(true, eventDao.updateYear("1254",2001));
        Assertions.assertEquals(2001,eventDao.getFromDB("1254").getYear());
    }

    @Test
    public void testUpdateYearDNE() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Assertions.assertEquals(false, eventDao.updateYear("1254",2001));
    }

    @Test
    public void testClear() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        eventDao.addToDB(event);
        Assertions.assertEquals(event.getEventID(), eventDao.getFromDB("1254").getEventID());
        eventDao.clear();
        Assertions.assertEquals(null, eventDao.getFromDB("1254"));
    }
}
