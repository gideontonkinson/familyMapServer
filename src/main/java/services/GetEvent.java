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
    public GetEventResult getEvent(String eventID, AuthToken authToken) {
        boolean commit = false;
        GetEventResult result;
        if(authToken == null){
            result = new GetEventResult("Error: You are not authorized", false);
            return result;
        }
        try {
            db.openConnection();
            EventDao eventDao = new EventDao(db.getConnection());
            Event event = eventDao.getFromDB(eventID);
            if(event == null){
                result = new GetEventResult("Error: No event with that ID", false);
            } else {
                if(!event.getUsername().equalsIgnoreCase(authToken.getUsername())){
                    result = new GetEventResult("Error: No event with that ID", false);
                } else {
                    result = new GetEventResult(event.getUsername(), event.getEventID(), event.getPersonID(),
                            event.getLatitude(), event.getLongitude(), event.getCountry(),
                            event.getCity(), event.getEventType(), event.getYear());
                }
            }
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
            result = new GetEventResult(e.getMessage(), false);
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
     * Services the GetEventRequest
     * @param authToken
     * @return GetEventResult if successful
     * @throws ResultException if the request was not a success
     */
    public GetEventsResult getEvents(AuthToken authToken) {
        GetEventsResult result;
        if(authToken == null){
            result = new GetEventsResult("Error: You are not authorized", false);
            return result;
        }
        boolean commit = false;
        try {
            db.openConnection();
            EventDao eventDao = new EventDao(db.getConnection());
            ArrayList<Event> events = (ArrayList<Event>) eventDao.getEventsForUser(authToken.getUsername());
            if (events == null) {
                result = new GetEventsResult("Error: No Events for that User", false);
            } else {
                result = new GetEventsResult(events);
            }
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
            result = new GetEventsResult(e.getMessage(), false);
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
