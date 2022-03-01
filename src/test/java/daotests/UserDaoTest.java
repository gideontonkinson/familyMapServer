package daotests;

import model.AuthToken;
import org.junit.jupiter.api.*;
import dataaccess.*;
import model.User;

public class UserDaoTest {
    private static Database db;
    private static UserDao userDao;

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
    public void testAddUserToDB() throws DaoException {
        userDao = new UserDao(db.getConnection());
        User user = new User("gmt26", "123", "gmt27@byu.edu", "Gideon","Tonkinson","M","1254");
        Assertions.assertEquals(true, userDao.addToDB(user));
    }

    @Test
    public void testAddSameUserToDB() throws DaoException {
        userDao = new UserDao(db.getConnection());
        User user = new User("gmt27", "123", "gmt27@byu.edu", "Gideon","Tonkinson","M","124");
        userDao.addToDB(user);
        Assertions.assertThrows(DaoException.class, () -> userDao.addToDB(user));
    }

    @Test
    public void testGetFromDB() throws DaoException {
        userDao = new UserDao(db.getConnection());
        User user = new User("gmt27", "123", "gmt27@byu.edu", "Gideon","Tonkinson","M","124");
        userDao.addToDB(user);
        Assertions.assertEquals(user.getUsername(), userDao.getFromDB("gmt27").getUsername());
    }

    @Test
    public void testGetFromDBDNE() throws DaoException {
        userDao = new UserDao(db.getConnection());
        Assertions.assertEquals(null, userDao.getFromDB("gmt27"));
    }

    @Test
    public void testValidate() throws DaoException {
        userDao = new UserDao(db.getConnection());
        User user = new User("gmt27", "123", "gmt27@byu.edu", "Gideon","Tonkinson","M","124");
        userDao.addToDB(user);
        Assertions.assertEquals(true, userDao.validate("gmt27", "123"));
    }

    @Test
    public void testValidateWrongPW() throws DaoException {
        userDao = new UserDao(db.getConnection());
        User user = new User("gmt27", "123", "gmt27@byu.edu", "Gideon","Tonkinson","M","124");
        userDao.addToDB(user);
        Assertions.assertEquals(false, userDao.validate("gmt27", "124"));
    }

    @Test
    public void testUpdateUsername() throws DaoException {
        userDao = new UserDao(db.getConnection());
        User user = new User("gmt27", "123", "gmt27@byu.edu", "Gideon","Tonkinson","M","124");
        userDao.addToDB(user);
        Assertions.assertEquals(true, userDao.updateUsername("gmt27", "gmt28"));
        Assertions.assertEquals("gmt28", userDao.getFromDB("gmt28").getUsername());
    }

    @Test
    public void testUpdateUsernameDNE() throws DaoException {
        userDao = new UserDao(db.getConnection());
        Assertions.assertEquals(false, userDao.updateUsername("gmt27", "gmt28"));
    }

    @Test
    public void testUpdatePassword() throws DaoException {
        userDao = new UserDao(db.getConnection());
        User user = new User("gmt27", "123", "gmt27@byu.edu", "Gideon","Tonkinson","M","124");
        userDao.addToDB(user);
        Assertions.assertEquals(true, userDao.updatePassword("gmt27", "124"));
        Assertions.assertEquals("124", userDao.getFromDB("gmt27").getPassword());
    }

    @Test
    public void testUpdatePasswordDNE() throws DaoException {
        userDao = new UserDao(db.getConnection());
        Assertions.assertEquals(false, userDao.updatePassword("gmt27", "124"));
    }

    @Test
    public void testUpdateEmail() throws DaoException {
        userDao = new UserDao(db.getConnection());
        User user = new User("gmt27", "123", "gmt27@byu.edu", "Gideon","Tonkinson","M","124");
        userDao.addToDB(user);
        Assertions.assertEquals(true, userDao.updateEmail("gmt27", "gmt27@gmail.com"));
        Assertions.assertEquals("gmt27@gmail.com", userDao.getFromDB("gmt27").getEmail());
    }

    @Test
    public void testUpdateEmailDNE() throws DaoException {
        userDao = new UserDao(db.getConnection());
        Assertions.assertEquals(false, userDao.updateEmail("gmt27", "gmt27@gmail.com"));
    }

    @Test
    public void testClear() throws DaoException {
        userDao = new UserDao(db.getConnection());
        User user = new User("gmt27", "123", "gmt27@byu.edu", "Gideon","Tonkinson","M","124");
        userDao.addToDB(user);
        Assertions.assertEquals(user.getUsername(), userDao.getFromDB("gmt27").getUsername());
        userDao.clear();
        Assertions.assertEquals(null, userDao.getFromDB("gmt27"));
    }
}
