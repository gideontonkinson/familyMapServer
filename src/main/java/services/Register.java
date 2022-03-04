package services;

import dataaccess.*;
import model.AuthToken;
import model.Person;
import model.User;
import requestresult.RegisterRequest;
import requestresult.RegisterResult;

/** Services an API request to register a new user and fill their data  */
public class Register {
    /** Connection to the family map database */
    private final Database db;

    /**
     * Creates a Register Service Object
     */
    public Register() {
        db = new Database();
    }

    /**
     * Creates a new user adds them the database and then generates an authToken for them
     * @param r Request
     * @return RegisterResult if successful
     */
    public RegisterResult register(RegisterRequest r) {
        boolean commit = false;
        RegisterResult result;
        try {
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            User u = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender());
            userDao.addToDB(u);
            String personID = u.getPersonID();
            AuthToken authToken = new AuthToken(r.getUsername());
            authTokenDao.addToDB(authToken);
            String authCode = authToken.getAuthtoken();
            commit = true;
            result = new RegisterResult(authCode, r.getUsername(), personID);
        } catch (DaoException e) {
            e.printStackTrace();
            result = new RegisterResult(e.getMessage(), false);
        } finally {
            try {
                db.closeConnection(commit);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }

        if(!result.isSuccess()){
            return result;
        }

        Fill fillService = new Fill();
        commit = false;
        try {
            fillService.setUp();
            Person p = fillService.generatePerson(r.getUsername(), r.getGender(), 4, 2000, true);
            fillService.cleanUp();
            db.openConnection();
            PersonDao personDao = new PersonDao(db.getConnection());
            personDao.addToDB(p);
            commit = true;
        } catch (DaoException e) {
            result = new RegisterResult("Error: Could not fill tree for User", false);
            e.printStackTrace();
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
