package requestresult;

import model.Person;
import java.util.ArrayList;

/** Serialized HTTP get persons result */
public class GetPersonsResult {
    private ArrayList<Person> data;
    private boolean success = true;
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
     * @param message
     * @param success
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
