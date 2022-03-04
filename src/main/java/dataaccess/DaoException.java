package dataaccess;

/**
 * Thrown when there is an error in accessing the database or adding to the database
 */
public class DaoException extends Exception {

    /**
     * Thrown if there is an exception in accessing the database
     * @param message message detailing the error
     */
    public DaoException (String message){
        super(message);
    }
}
