package requestresult;

import model.Event;

import java.util.ArrayList;

public class GetEventsResult {
    private ArrayList<Event> data;
    private boolean success = true;
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
     * @param message
     * @param success
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
