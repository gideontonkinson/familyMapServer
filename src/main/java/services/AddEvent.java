package services;

import dataaccess.DaoException;
import dataaccess.Database;
import dataaccess.EventDao;
import model.AuthToken;
import model.Event;
import requestresult.*;

/** Services an API request to add an event from the database */
public class AddEvent {
    /** Connection to the family map database */
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
     * @return AddEventResult with a message if unsucessful and an event if it was
     */
    public AddEventResult addEvent(AddEventRequest r, AuthToken authToken) {
        Event event = new Event(r.getAssociatedUsername(), r.getPersonID(), r.getLatitude(),
                r.getLongitude(), r.getCountry(), r.getCity(), r.getEventType(), r.getYear());
        boolean commit = false;
        AddEventResult result;
        if(authToken == null){
            result = new AddEventResult("Error: You are not authorized", false);
            return result;
        }
        try {
            db.openConnection();
            EventDao eventDao = new EventDao(db.getConnection());
            eventDao.addToDB(event);
            result = new AddEventResult(event);
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
            result = new AddEventResult(e.getMessage(), false);
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
