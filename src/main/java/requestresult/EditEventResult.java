package requestresult;

import model.Event;

public class EditEventResult {
     private Event event;
     private boolean success = true;

     /**
      * Creates a successful EditEventResult
      * @param event
      */
     public EditEventResult(Event event) {
          this.event = event;
     }
}
