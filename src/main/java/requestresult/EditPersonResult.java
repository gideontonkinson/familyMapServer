package requestresult;

import model.Person;

public class EditPersonResult {
    private Person person;
    private boolean success = true;

    /**
     * Creates a successful EditPersonResult
     * @param person
     */
    public EditPersonResult(Person person) {
        this.person = person;
    }
}
