package services;

import dataaccess.DaoException;
import dataaccess.Database;
import dataaccess.EventDao;
import model.AuthToken;
import model.Event;
import requestresult.*;

import java.util.ArrayList;

public class GetEvent {

    private final Database db;

    /**
     * Creates a GetEvent Service Object
     */
    public GetEvent() {
        db = new Database();
    }

    /**
     * Services the GetEventRequest
     * @param eventID
     * @return GetEventResult if successful
     * @throws ResultException if the request was not a success
     */
    public GetEventResult getEvent(String eventID) throws ResultException {
        Event event;
        boolean commit = false;
        try {
            db.openConnection();
            EventDao eventDao = new EventDao(db.getConnection());
            event = eventDao.getFromDB(eventID);
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
        if(eventID == null){
            throw new ResultException("No event to with that ID");
        }
        GetEventResult eventResult = new GetEventResult(event.getUsername(), event.getEventID(), event.getPersonID(),
                                                        event.getLatitude(), event.getLongitude(), event.getCountry(),
                                                        event.getCity(), event.getEventType(), event.getYear());
        return eventResult;
    }

    /**
     * Services the GetEventRequest
     * @param authToken
     * @return GetEventResult if successful
     * @throws ResultException if the request was not a success
     */
    public GetEventsResult getEvents(AuthToken authToken) throws ResultException {
        ArrayList<Event> events;
        boolean commit = false;
        try {
            db.openConnection();
            EventDao eventDao = new EventDao(db.getConnection());
            events = (ArrayList<Event>) eventDao.getEventsForUser(authToken.getUsername());
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
        if (events == null) {
            throw new ResultException("No Events for that User");
        }
        GetEventsResult eventsResult = new GetEventsResult(events);

        return eventsResult;
    }
}
