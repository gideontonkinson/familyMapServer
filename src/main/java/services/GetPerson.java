package services;

import dataaccess.*;
import model.AuthToken;
import model.Person;
import requestresult.GetEventResult;
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
    public GetPersonResult getPerson(String personID, AuthToken authToken) {
        boolean commit = false;
        GetPersonResult result;
        if(authToken == null){
            result = new GetPersonResult("Error: You are not authorized", false);
            return result;
        }
        try {
            db.openConnection();
            PersonDao personDao = new PersonDao(db.getConnection());
            Person p = personDao.getFromDB(personID);
            if(p == null) {
                result = new GetPersonResult("Error: No person to with that ID", false);
            } else {
                if(!p.getUsername().equalsIgnoreCase(authToken.getUsername())){
                    result = new GetPersonResult("Error: No person to with that ID", false);
                } else {
                    result = new GetPersonResult(p.getUsername(), p.getPersonID(), p.getFirstName(),
                            p.getLastName(), p.getGender(), p.getFatherID(),
                            p.getMotherID(), p.getSpouseID());
                }
            }
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
            result = new GetPersonResult(e.getMessage(), false);
        } finally {
            try {
                db.closeConnection(commit);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * Gets all Persons from the database for a User (given their AuthToken) and packages it
     * @param authToken
     * @return GetPersonsResult if successful
     * @throws ResultException if the request was not a success
     */
    public GetPersonsResult getPersons(AuthToken authToken) {
        GetPersonsResult result;
        if(authToken == null){
            result = new GetPersonsResult("Error: You are not authorized", false);
            return result;
        }
        boolean commit = false;
        try {
            db.openConnection();
            PersonDao personDao = new PersonDao(db.getConnection());
            ArrayList<Person> persons = (ArrayList<Person>) personDao.getPersonsForUser(authToken.getUsername());
            if(persons == null){
                result = new GetPersonsResult("Error: No Persons for that User", false);
            } else {
                result = new GetPersonsResult(persons);
            }
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
            result = new GetPersonsResult(e.getMessage(), false);
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
