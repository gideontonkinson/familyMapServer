package services;

import dataaccess.Database;
import requestresult.FillResult;
import requestresult.ResultException;

public class Fill {
    private final Database db;

    /**
     * Creates an Fill Service Object
     */
    public Fill() {
        db = new Database();
    }

    /**
     * Services the FillRequest
     * @return FillResult if successful
     * @throws ResultException if the request was not a success
     */
    FillResult fill() throws ResultException {
        return null;
    }
}
