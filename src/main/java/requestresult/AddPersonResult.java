package requestresult;

import model.Person;

/** Serialized HTTP add event result */
public class AddPersonResult {
    /** Person that was added */
    private Person person;
    /** Boolean detailing if the request was succesful */
    private boolean success = true;
    /** Message detailing what happened */
    private String message;

    /**
     * Creates a successful AddPersonResult
     * @param person Person to add
     */
    public AddPersonResult(Person person) {
        this.person = person;
    }

    /**
     * Creates a fail AddPersonResult
     * @param message message detailing why it failed
     * @param success bool true if succeeded
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
