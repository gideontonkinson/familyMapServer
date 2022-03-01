package services;

import dataaccess.*;
import model.AuthToken;
import model.Person;
import requestresult.GetPersonResult;
import requestresult.GetPersonsResult;
import requestresult.ResultException;

import java.util.ArrayList;

public class GetPerson {
    private final Database db;

    /**
     * Creates an GetPerson Service Object
     */
    public GetPerson() {
        db = new Database();
    }

    /**
     * Gets a single Person from the database and packages it
     * @param personID
     * @return GetPersonResult if successful
     * @throws ResultException if the request was not a success
     */
    public GetPersonResult getPerson(String personID) throws ResultException {
        Person p;
        boolean commit = false;
        try {
            db.openConnection();
            PersonDao personDao = new PersonDao(db.getConnection());
            p = personDao.getFromDB(personID);
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
        if(personID == null){
            throw new ResultException("Error: No person to with that ID");
        }
        GetPersonResult personResult = new GetPersonResult(p.getUsername(), p.getPersonID(), p.getFirstName(),
                                                            p.getLastName(), p.getGender(), p.getFatherID(),
                                                            p.getMotherID(), p.getSpouseID());
        return personResult;
    }

    /**
     * Gets all Persons from the database for a User (given their AuthToken) and packages it
     * @param authToken
     * @return GetPersonsResult if successful
     * @throws ResultException if the request was not a success
     */
    public GetPersonsResult getPersons(AuthToken authToken) throws ResultException {
        ArrayList<Person> persons;
        boolean commit = false;
        try {
            db.openConnection();
            PersonDao personDao = new PersonDao(db.getConnection());
            persons = (ArrayList<Person>) personDao.getPersonsForUser(authToken.getUsername());
            db.closeConnection(commit);
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
        if (persons == null) {
            throw new ResultException("Error: No Persons for that User");
        }
        GetPersonsResult personsResult = new GetPersonsResult(persons);
        return personsResult;
    }
}
