package services;

import dataaccess.*;
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
    public LoadResult load (LoadRequest r) throws ResultException{
        boolean commit = false;
        try {
            db.openConnection();
            PersonDao personDao = new PersonDao(db.getConnection());
            UserDao userDao = new UserDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            for (Person person : r.getPersons()) {
                personDao.addToDB(person);
            }
            for (User user : r.getUsers()) {
                userDao.addToDB(user);
            }
            for (Event event : r.getEvents()) {
                eventDao.addToDB(event);
            }
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
        String message = "Successfully added " + r.getUsers().size() + " users, " + r.getPersons().size() + " persons, "
                            + r.getEvents().size() + " events.";
        return new LoadResult(message);
    }
}
