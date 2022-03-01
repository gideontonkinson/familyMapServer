package daotests;

import model.AuthToken;
import org.junit.jupiter.api.*;
import dataaccess.AuthTokenDao;
import dataaccess.DaoException;
import dataaccess.Database;

public class AuthTokenDaoTest {
    private static Database db;
    private static AuthTokenDao authTokenDao;


    @BeforeAll
    static void createDatabase() throws DaoException {
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
        Assertions.assertEquals(authToken.getUsername(), authTokenDao.getFromDB("1254").getUsername());
    }

    @Test
    public void testGetFromDBDNE() throws DaoException {
        authTokenDao = new AuthTokenDao(db.getConnection());
        Assertions.assertEquals(null, authTokenDao.getFromDB("1254"));
    }

    @Test
    public void testUpdateUsername() throws DaoException {
        authTokenDao = new AuthTokenDao(db.getConnection());
        AuthToken authToken = new AuthToken("1254", "gmt27");
        authTokenDao.addToDB(authToken);
        Assertions.assertEquals(true, authTokenDao.updateUsername("gmt27", "gmt28"));
        Assertions.assertEquals("gmt28", authTokenDao.getAuthTokenFromDB("gmt28").getUsername());
    }

    @Test
    public void testUpdateUsernameDNE() throws DaoException {
        authTokenDao = new AuthTokenDao(db.getConnection());
        Assertions.assertEquals(false, authTokenDao.updateUsername("gmt27", "gmt28"));
    }

    @Test
    public void testClear() throws DaoException {
        authTokenDao = new AuthTokenDao(db.getConnection());
        AuthToken authToken = new AuthToken("1254", "gmt27");
        authTokenDao.addToDB(authToken);
        Assertions.assertEquals(authToken.getUsername(), authTokenDao.getAuthTokenFromDB("gmt27").getUsername());
        authTokenDao.clear();
        Assertions.assertEquals(null, authTokenDao.getFromDB("gmt27"));
    }
}
