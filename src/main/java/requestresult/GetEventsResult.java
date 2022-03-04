package requestresult;

import model.Event;
import java.util.ArrayList;

/** Serialized HTTP get events result */
public class GetEventsResult {
    /** List of Events */
    private ArrayList<Event> data;
    /** Boolean detailing if the request was succesful */
    private boolean success = true;
    /** Message detailing what happened */
    private String message;

    /**
     * Creates a successful GetEventsResult
     * @param data
     */
    public GetEventsResult(ArrayList<Event> data) {
        this.data = data;
    }

    /**
     * Creates a fail GetEventsResult
     * @param message message detailing why it failed
     * @param success bool true if succeeded
     */
    public GetEventsResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }


}
