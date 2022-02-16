package model;

/**
 * Stores the data of an object from the database or a new object created by the user
 */
public interface Model {
    /**
     * Gets the username of the owner of the model
     * @return String of the username
     */
    String getUsername();
}
