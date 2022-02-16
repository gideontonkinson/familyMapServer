package requestresult;

import model.Person;

public class GetPersonsResult {
    private Person[] data;
    private boolean success = true;

    /**
     * Creates a successful GetPersonsResult
     * @param data
     */
    public GetPersonsResult(Person[] data) {
        this.data = data;
    }
}
