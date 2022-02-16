package daotests;

import model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dataaccess.AuthTokenDao;
import dataaccess.DaoException;
import dataaccess.Database;

public class AuthTokenDaoTest {
    private static Database db;
    private static AuthTokenDao authTokenDao;


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
    public void testAddAuthTokenToDB() throws DaoException {
        authTokenDao = new AuthTokenDao(db.getConnection());
        AuthToken authToken = new AuthToken("1254", "gmt27");
        Assertions.assertEquals(true, authTokenDao.addToDB(authToken));
    }

    @Test
    public void testAddSameAuthTokenToDB() throws DaoException {
        authTokenDao = new AuthTokenDao(db.getConnection());
        AuthToken authToken = new AuthToken("1254", "gmt27");
        authTokenDao.addToDB(authToken);
        Assertions.assertThrows(DaoException.class, () -> authTokenDao.addToDB(authToken));
    }

    @Test
    public void testGetFromDB() throws DaoException {
        authTokenDao = new AuthTokenDao(db.getConnection());
        AuthToken authToken = new AuthToken("1254", "gmt27");
        authTokenDao.addToDB(authToken);
        Assertions.assertEquals(authToken.getUsername(), authTokenDao.getFromDB("gmt27").getUsername());
    }

    @Test
    public void testGetFromDBDNE() throws DaoException {
        authTokenDao = new AuthTokenDao(db.getConnection());
        Assertions.assertEquals(null, authTokenDao.getFromDB("gmt27"));
    }

    @Test
    public void testClear() throws DaoException {
        authTokenDao = new AuthTokenDao(db.getConnection());
        AuthToken authToken = new AuthToken("1254", "gmt27");
        authTokenDao.addToDB(authToken);
        Assertions.assertEquals(authToken.getUsername(), authTokenDao.getFromDB("gmt27").getUsername());
        authTokenDao.clear();
        Assertions.assertEquals(null, authTokenDao.getFromDB("gmt27"));
    }
}
