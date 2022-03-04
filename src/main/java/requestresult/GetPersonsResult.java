package requestresult;

import model.Person;
import java.util.ArrayList;

/** Serialized HTTP get persons result */
public class GetPersonsResult {
    /** List of Persons */
    private ArrayList<Person> data;
    /** Boolean detailing if the request was succesful */
    private boolean success = true;
    /** Message detailing what happened */
    private String message;

    /**
     * Creates a successful GetPersonsResult
     * @param data
     */
    public GetPersonsResult(ArrayList<Person> data) {
        this.data = data;
    }

    /**
     * Creates a fail GetPersonsResult
     * @param message message detailing why it failed
     * @param success bool true if succeeded
     */
    public GetPersonsResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
