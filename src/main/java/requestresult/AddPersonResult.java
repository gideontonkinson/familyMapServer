package requestresult;

import model.Person;

/** Serialized HTTP add event result */
public class AddPersonResult {
    private Person person;
    private boolean success = true;
    private String message;

    /**
     * Creates a successful AddPersonResult
     * @param person
     */
    public AddPersonResult(Person person) {
        this.person = person;
    }

    /**
     * Creates a fail AddPersonResult
     * @param message
     * @param success
     */
    public AddPersonResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public Person getPerson() {
        return person;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
