package daotests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        db.createTables();
    }

    @AfterEach
    public void tearDown() throws DaoException {
        db.closeConnection(false);
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
    public void testClear() throws DaoException {
        userDao = new UserDao(db.getConnection());
        User user = new User("gmt27", "123", "gmt27@byu.edu", "Gideon","Tonkinson","M","124");
        userDao.addToDB(user);
        Assertions.assertEquals(user.getUsername(), userDao.getFromDB("gmt27").getUsername());
        userDao.clear();
        Assertions.assertEquals(null, userDao.getFromDB("gmt27"));
    }
}
