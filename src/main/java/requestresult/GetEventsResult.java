package requestresult;

import model.Event;

public class GetEventsResult {
    private Event[] data;
    private boolean success = true;

    /**
     * Creates a successful GetEventsResult
     * @param data
     */
    public GetEventsResult(Event[] data) {
        this.data = data;
    }
}
