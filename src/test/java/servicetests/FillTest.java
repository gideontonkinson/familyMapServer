package servicetests;

import dataaccess.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Fill;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.ArrayList;

public class FillTest {
    private static Database db;
    private static UserDao userDao;
    private static PersonDao personDao;

    @BeforeAll
    public static void createData() throws DaoException{
        db = new Database();
        db.openConnection();
        db.closeConnection(true);
    }

    @BeforeEach
    public void setUp() throws DaoException {
        db.openConnection();
        db.createTablesForTesting();
        userDao = new UserDao(db.getConnection());
        User user = new User("gmt27", "123456", "gmt27@byu.edu", "Gideon","Tonkinson","m");
        userDao.addToDB(user);
        db.closeConnection(true);
    }

    @Test
    public void testFillZeroGens() throws DaoException{
        Fill service = new Fill();
        db.openConnection();
        userDao = new UserDao(db.getConnection());
        User u = userDao.getFromDB("gmt27");
        db.closeConnection(true);
        service.setUp();
        Person p = service.generatePerson("gmt27", "m", 0, 2000, true);
        service.cleanUp();
        db.openConnection();
        personDao = new PersonDao(db.getConnection());
        personDao.addToDB(p);
        ArrayList<Person> persons = (ArrayList<Person>) personDao.getPersonsForUser("gmt27");
        db.closeConnection(true);
        Assertions.assertEquals(1, persons.size());
        Assertions.assertEquals("Gideon", p.getFirstName());
        Assertions.assertEquals("Tonkinson", p.getLastName());
        Assertions.assertEquals("gmt27", p.getUsername());
        Assertions.assertEquals(u.getPersonID(), p.getPersonID());
    }

    @Test
    public void testFillFourGens() throws DaoException{
        Fill service = new Fill();
        db.openConnection();
        userDao = new UserDao(db.getConnection());
        User u = userDao.getFromDB("gmt27");
        db.closeConnection(true);
        service.setUp();
        Person p = service.generatePerson("gmt27", "m", 4, 2000, true);
        service.cleanUp();
        db.openConnection();
        personDao = new PersonDao(db.getConnection());
        personDao.addToDB(p);
        ArrayList<Person> persons = (ArrayList<Person>) personDao.getPersonsForUser("gmt27");
        db.closeConnection(true);
        Assertions.assertEquals(31, persons.size());
    }
}
