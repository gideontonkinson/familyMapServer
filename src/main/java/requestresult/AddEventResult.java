package requestresult;

import model.Event;

public class AddEventResult {
    private Event event;
    private boolean success = true;

    /**
     * Creates a successful AddEventResult
     * @param event
     */
    public AddEventResult(Event event) {
        this.event = event;
    }


}
