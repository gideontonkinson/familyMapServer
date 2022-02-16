package requestresult;

import model.Person;

public class AddPersonResult {
    private Person person;
    private boolean success = true;

    /**
     * Creates a successful AddPersonResult
     * @param person
     */
    public AddPersonResult(Person person) {
        this.person = person;
    }
}
