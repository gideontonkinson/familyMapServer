package daotests;


import dataaccess.DaoException;
import dataaccess.Database;
import dataaccess.EventDao;
import dataaccess.EventDao;
import model.Event;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        db.createTables();
    }

    @AfterEach
    public void tearDown() throws DaoException {
        db.closeConnection(false);
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
    public void testClear() throws DaoException {
        eventDao = new EventDao(db.getConnection());
        Event event = new Event("1254", "gmt27", "1245",11,23,"France", "Paris", "Birth", 2000);
        eventDao.addToDB(event);
        Assertions.assertEquals(event.getEventID(), eventDao.getFromDB("1254").getEventID());
        eventDao.clear();
        Assertions.assertEquals(null, eventDao.getFromDB("1254"));
    }
}
