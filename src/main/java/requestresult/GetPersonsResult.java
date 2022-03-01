package requestresult;

import model.Person;

import java.util.ArrayList;

public class GetPersonsResult {
    private ArrayList<Person> data;
    private boolean success = true;

    /**
     * Creates a successful GetPersonsResult
     * @param data
     */
    public GetPersonsResult(ArrayList<Person> data) {
        this.data = data;
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }
}
