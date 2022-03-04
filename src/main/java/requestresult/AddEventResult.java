package requestresult;

import model.Event;

/** Serialized HTTP add event result */
public class AddEventResult {
    private Event event;
    private boolean success = true;
    private String message;

    /**
     * Creates a successful AddEventResult
     * @param event
     */
    public AddEventResult(Event event) {
        this.event = event;
    }

    /**
     * Creates a fail AddEventResult
     * @param message
     * @param success
     */
    public AddEventResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public Event getEvent() {
        return event;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
