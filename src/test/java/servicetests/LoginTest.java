package servicetests;

import dataaccess.*;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.LoginRequest;
import requestresult.LoginResult;
import services.Login;

public class LoginTest {
    private static Database db;
    private static UserDao userDao;
    private static AuthTokenDao authTokenDao;

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
        userDao = new UserDao(db.getConnection());
        authTokenDao = new AuthTokenDao(db.getConnection());
        User user = new User("gmt27", "123456", "gmt27@byu.edu", "Gideon","Tonkinson","m");
        authTokenDao.addToDB(new AuthToken("1234", "gmt27"));
        userDao.addToDB(user);
        db.closeConnection(true);
    }

    @Test
    public void testLogin(){
        Login service = new Login();
        LoginRequest request = new LoginRequest("gmt27", "123456");
        LoginResult result = service.login(request);
        Assertions.assertEquals(true, result.isSuccess());
        Assertions.assertEquals("gmt27", result.getUsername());
    }

    @Test
    public void testLoginInvalidCredentials(){
        Login service = new Login();
        LoginRequest request = new LoginRequest("gmt27", "123457");
        LoginResult result = service.login(request);
        Assertions.assertEquals(false, result.isSuccess());
    }

}
