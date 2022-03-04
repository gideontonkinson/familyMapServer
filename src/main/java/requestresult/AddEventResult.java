package requestresult;

import model.Event;

/** Serialized HTTP add event result */
public class AddEventResult {
    /** Event that was added */
    private Event event;
    /** Boolean detailing if the request was succesful */
    private boolean success = true;
    /** Message detailing what happened */
    private String message;

    /**
     * Creates a successful AddEventResult
     * @param event Event to return
     */
    public AddEventResult(Event event) {
        this.event = event;
    }

    /**
     * Creates a fail AddEventResult
     * @param message message detailing why it failed
     * @param success bool true if succeeded
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
