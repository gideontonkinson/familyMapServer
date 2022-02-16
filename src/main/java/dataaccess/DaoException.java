package dataaccess;

/**
 * Thrown when there is an error in accessing the database or adding to the database
 */
public class DaoException extends Exception {

    /**
     * Can return a custom message detailing the error
     * @param message
     */
    public DaoException (String message){
        super(message);
    }
}
