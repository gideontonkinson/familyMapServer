package requestresult;

import model.Event;

import java.util.ArrayList;

public class GetEventsResult {
    private ArrayList<Event> data;
    private boolean success = true;

    /**
     * Creates a successful GetEventsResult
     * @param data
     */
    public GetEventsResult(ArrayList<Event> data) {
        this.data = data;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }
}
