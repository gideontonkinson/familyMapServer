package daotests;

import model.Person;
import org.junit.jupiter.api.*;
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
    public void testGetPersonsForUser() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Person person1 = new Person("1254", "gmt27", "Gideon","Tonkinson","M");
        Person person2 = new Person("1255", "gmt27", "Tina","Tonkinson","F");
        Person person3 = new Person("1256", "jmt28", "Jimmothy","Munroe","M");
        personDao.addToDB(person1);
        personDao.addToDB(person2);
        personDao.addToDB(person3);
        Assertions.assertEquals(2, personDao.getPersonsForUser("gmt27").size());
    }

    @Test
    public void testGetPersonsForUserDNE() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Assertions.assertEquals(null, personDao.getPersonsForUser("gmt27"));
    }

    @Test
    public void testClearPersonsForUser() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Person person1 = new Person("1254", "gmt27", "Gideon","Tonkinson","M");
        Person person2 = new Person("1255", "gmt27", "Tina","Tonkinson","F");
        Person person3 = new Person("1256", "jmt28", "Jimmothy","Munroe","M");
        personDao.addToDB(person1);
        personDao.addToDB(person2);
        personDao.addToDB(person3);
        personDao.clearPersonsForUser("gmt27");
        Assertions.assertEquals(null, personDao.getPersonsForUser("gmt27"));
        Assertions.assertEquals(1, personDao.getPersonsForUser("jmt28").size());
    }

    @Test
    public void testUpdateUsername() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Person person1 = new Person("1254", "gmt27", "Gideon","Tonkinson","M");
        Person person2 = new Person("1255", "gmt27", "Tina","Tonkinson","F");
        Person person3 = new Person("1256", "jmt28", "Jimmothy","Munroe","M");
        personDao.addToDB(person1);
        personDao.addToDB(person2);
        personDao.addToDB(person3);
        Assertions.assertEquals(true, personDao.updateUsername("gmt27","gmt28"));
        Assertions.assertEquals(2, personDao.getPersonsForUser("gmt28").size());
    }

    @Test
    public void testUpdateUsernameDNE() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Assertions.assertEquals(false, personDao.updateUsername("gmt27","gmt28"));
    }

    @Test
    public void testUpdateFather() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Person person1 = new Person("1254", "gmt27", "Gideon","Tonkinson","M");
        personDao.addToDB(person1);
        Assertions.assertEquals(true, personDao.updateFather("1254","1255"));
        Assertions.assertEquals("1255", personDao.getFromDB("1254").getFatherID());
    }

    @Test
    public void testUpdateFatherDNE() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Assertions.assertEquals(false, personDao.updateFather("1254","1255"));
    }

    @Test
    public void testUpdateMother() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Person person1 = new Person("1254", "gmt27", "Gideon","Tonkinson","M");
        personDao.addToDB(person1);
        Assertions.assertEquals(true, personDao.updateMother("1254","1255"));
        Assertions.assertEquals("1255", personDao.getFromDB("1254").getMotherID());
    }

    @Test
    public void testUpdateMotherDNE() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Assertions.assertEquals(false, personDao.updateMother("1254","1255"));
    }

    @Test
    public void testUpdateSpouse() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Person person1 = new Person("1254", "gmt27", "Gideon","Tonkinson","M");
        personDao.addToDB(person1);
        Assertions.assertEquals(true, personDao.updateSpouse("1254","1255"));
        Assertions.assertEquals("1255", personDao.getFromDB("1254").getSpouseID());
    }

    @Test
    public void testUpdateSpouseDNE() throws DaoException {
        personDao = new PersonDao(db.getConnection());
        Assertions.assertEquals(false, personDao.updateSpouse("1254","1255"));
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
