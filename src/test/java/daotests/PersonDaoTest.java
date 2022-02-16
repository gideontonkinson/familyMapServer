package daotests;

import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dataaccess.*;

public class PersonDaoTest {
    private static Database db;
    private static PersonDao personDao;

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
    public void testAddPersonToDB() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Person person = new Person("1254", "gmt27", "Gideon","Tonkinson","M");
        Assertions.assertEquals(true, personDao.addToDB(person));
    }

    @Test
    public void testAddSamePersonToDB() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Person person = new Person("1254", "gmt27", "Gideon","Tonkinson","M");
        personDao.addToDB(person);
        Assertions.assertThrows(DaoException.class, () -> personDao.addToDB(person));
    }

    @Test
    public void testGetFromDB() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Person person = new Person("1254", "gmt27", "Gideon","Tonkinson","M");
        personDao.addToDB(person);
        Assertions.assertEquals(person.getPersonID(), personDao.getFromDB("1254").getPersonID());
    }

    @Test
    public void testGetFromDBDNE() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Assertions.assertEquals(null, personDao.getFromDB("1254"));
    }

    @Test
    public void testClear() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Person person = new Person("1254", "gmt27", "Gideon","Tonkinson","M");
        personDao.addToDB(person);
        Assertions.assertEquals(person.getPersonID(), personDao.getFromDB("1254").getPersonID());
        personDao.clear();
        Assertions.assertEquals(null, personDao.getFromDB("1254"));
    }
}
