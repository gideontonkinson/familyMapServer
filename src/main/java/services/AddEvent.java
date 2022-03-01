package services;

import dataaccess.DaoException;
import dataaccess.Database;
import dataaccess.EventDao;
import dataaccess.PersonDao;
import model.Event;
import model.Person;
import requestresult.AddEventRequest;
import requestresult.AddEventResult;
import requestresult.AddPersonResult;
import requestresult.ResultException;

public class AddEvent {

    private final Database db;

    /**
     * Creates an AddEvent Service Object
     */
    public AddEvent() {
        db = new Database();
    }

    /**
     * Services the AddEventRequest
     * @param r
     * @return AddEventResult if successful
     * @throws ResultException if the request was not a success
     */
    AddEventResult addEvent(AddEventRequest r) throws ResultException {
        Event event = new Event(r.getAssociatedUsername(), r.getPersonID(), r.getLatitude(),
                r.getLongitude(), r.getCountry(), r.getCity(), r.getEventType(), r.getYear());
        boolean commit = false;
        try {
            db.openConnection();
            EventDao eventDao = new EventDao(db.getConnection());
            eventDao.addToDB(event);
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
        return new AddEventResult(event);
    }
}
