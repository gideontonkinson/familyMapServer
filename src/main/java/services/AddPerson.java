package services;

import dataaccess.DaoException;
import dataaccess.Database;
import dataaccess.PersonDao;
import model.Person;
import requestresult.AddPersonRequest;
import requestresult.AddPersonResult;
import requestresult.ResultException;

public class AddPerson {

    private final Database db;

    /**
     * Creates an AddPerson Service Object
     */
    public AddPerson() {
        db = new Database();
    }

    /**
     * Services the AddPersonRequest
     * @param r
     * @return AddPersonResult if successful
     * @throws ResultException if the request was not a success
     */
    public AddPersonResult addPerson(AddPersonRequest r) throws ResultException {
        Person p = new Person(r.getAssociatedUsername(), r.getFirstName(), r.getLastName(),
                r.getGender(), r.getFatherID(), r.getMotherID(), r.getSpouseID());
        boolean commit = false;
        try {
            db.openConnection();
            PersonDao personDao = new PersonDao(db.getConnection());
            personDao.addToDB(p);
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ResultException(e.getMessage());
        } finally {
            try {
                db.closeConnection(commit);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return new AddPersonResult(p);
    }
}
