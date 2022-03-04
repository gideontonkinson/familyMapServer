package services;

import dataaccess.DaoException;
import dataaccess.Database;
import dataaccess.PersonDao;
import model.AuthToken;
import model.Person;
import requestresult.AddPersonRequest;
import requestresult.AddPersonResult;

/** Services an API request to add a person from the database */
public class AddPerson {
    /** Connection to the family map database */
    private final Database db;

    /**
     * Creates an AddPerson Service Object
     */
    public AddPerson() {
        db = new Database();
    }

    /**
     * Services the AddPersonRequest
     * @param r Request
     * @param authToken AuthToken of User
     * @return AddPersonResult if successful
     */
    public AddPersonResult addPerson(AddPersonRequest r, AuthToken authToken) {
        Person p = new Person(r.getAssociatedUsername(), r.getFirstName(), r.getLastName(),
                r.getGender(), r.getFatherID(), r.getMotherID(), r.getSpouseID());
        AddPersonResult result;
        if(authToken == null){
            result = new AddPersonResult("Error: You are not authorized", false);
            return result;
        }
        boolean commit = false;
        try {
            db.openConnection();
            PersonDao personDao = new PersonDao(db.getConnection());
            personDao.addToDB(p);
            result = new AddPersonResult(p);
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
            result = new AddPersonResult(e.getMessage(), false);
        } finally {
            try {
                db.closeConnection(commit);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
