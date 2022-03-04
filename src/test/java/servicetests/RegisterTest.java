package servicetests;

import dataaccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.RegisterRequest;
import requestresult.RegisterResult;
import services.Register;

/** Class to test the Register service */
public class RegisterTest {
    private static Database db;
    private static EventDao eventDao;
    private static PersonDao personDao;

    @BeforeAll
    public static void createData() throws DaoException {
        db = new Database();
        db.openConnection();
        db.closeConnection(true);
    }

    @BeforeEach
    public void setUp() throws DaoException {
        db.openConnection();
        db.createTablesForTesting();
        eventDao = new EventDao(db.getConnection());
        personDao = new PersonDao(db.getConnection());
        db.closeConnection(true);
    }

    @Test
    public void TestRegister() throws DaoException {
        Register service = new Register();
        RegisterRequest request = new RegisterRequest("gmt27", "123456", "gmt27@gmail.com", "Gideon", "Tonkinson", "m");
        RegisterResult result = service.register(request);
        Assertions.assertEquals(true, result.isSuccess());
        Assertions.assertEquals("gmt27", result.getUsername());
        db.openConnection();
        eventDao = new EventDao(db.getConnection());
        personDao = new PersonDao(db.getConnection());
        Assertions.assertEquals(31, personDao.getPersonsForUser("gmt27").size());
        Assertions.assertEquals(91, eventDao.getEventsForUser("gmt27").size());
        db.closeConnection(true);
    }

    @Test
    public void TestRegisterUserTwice(){
        Register service = new Register();
        RegisterRequest request = new RegisterRequest("gmt27", "123456", "gmt27@gmail.com", "Gideon", "Tonkinson", "m");
        service.register(request);
        RegisterResult result = service.register(request);
        Assertions.assertEquals(false, result.isSuccess());
    }
}
