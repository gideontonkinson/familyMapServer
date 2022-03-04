package servicetests;

import dataaccess.DaoException;
import dataaccess.Database;
import dataaccess.PersonDao;
import model.AuthToken;
import model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestresult.GetPersonResult;
import requestresult.GetPersonsResult;
import services.GetPerson;

public class GetPersonTest {
    private static Database db;
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
        personDao = new PersonDao(db.getConnection());
        Person person1 = new Person("1254", "gmt27", "Gideon","Tonkinson","M");
        Person person2 = new Person("1255", "gmt27", "Tina","Tonkinson","F");
        Person person3 = new Person("1256", "jmt28", "Jimmothy","Munroe","M");
        personDao.addToDB(person1);
        personDao.addToDB(person2);
        personDao.addToDB(person3);
        db.closeConnection(true);
    }

    @Test
    public void getPersonTest(){
        GetPerson service = new GetPerson();
        AuthToken authToken = new AuthToken("1248", "gmt27");
        GetPersonResult result = service.getPerson("1254", authToken);
        Assertions.assertEquals("1254", result.getPersonID());
        Assertions.assertEquals(true, result.isSuccess());
    }

    @Test
    public void getPersonTestDNE(){
        GetPerson service = new GetPerson();
        AuthToken authToken = new AuthToken("1248", "gmt28");
        GetPersonResult result = service.getPerson("1253", authToken);
        Assertions.assertEquals("Error: No person with that ID", result.getMessage());
        Assertions.assertEquals(false, result.isSuccess());
    }

    @Test
    public void getPersonsTest(){
        GetPerson service = new GetPerson();
        AuthToken authToken = new AuthToken("1248", "gmt27");
        GetPersonsResult result = service.getPersons(authToken);
        Assertions.assertEquals(2, result.getData().size());
        Assertions.assertEquals(true, result.isSuccess());
    }

    @Test
    public void getPersonsTestDNE(){
        GetPerson service = new GetPerson();
        AuthToken authToken = new AuthToken("1248", "gmt28");
        GetPersonsResult result = service.getPersons(authToken);
        Assertions.assertEquals("Error: No Persons for that User", result.getMessage());
        Assertions.assertEquals(false, result.isSuccess());
    }
}
