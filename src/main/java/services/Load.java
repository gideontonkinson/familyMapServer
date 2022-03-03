package services;

import dataaccess.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import requestresult.LoadRequest;
import requestresult.LoadResult;
import requestresult.ResultException;

public class Load {
    private final Database db;

    /**
     * Creates an Load Service Object
     */
    public Load() {
        db = new Database();
    }

    /**
     * Takes in the load request and adds the data to the database
     * @param r LoadRequest
     * @return LoadResult with a message that it was successful
     * @throws ResultException if an error was encountered
     */
    public LoadResult load (LoadRequest r) {
        boolean commit = false;
        LoadResult result;
        try {
            db.openConnection();
            PersonDao personDao = new PersonDao(db.getConnection());
            UserDao userDao = new UserDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            personDao.clear();
            eventDao.clear();
            userDao.clear();
            authTokenDao.clear();

            for (Person person : r.getPersons()) {
                personDao.addToDB(person);
                AuthToken token = new AuthToken(person.getUsername());
                authTokenDao.addToDB(token);
            }
            for (User user : r.getUsers()) {
                userDao.addToDB(user);
            }
            for (Event event : r.getEvents()) {
                eventDao.addToDB(event);
            }
            String message = "Successfully added " + r.getUsers().length + " users, " + r.getPersons().length + " persons, and "
                    + r.getEvents().length + " events.";
            result = new LoadResult(message);
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
            result = new LoadResult(e.getMessage(), false);
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
