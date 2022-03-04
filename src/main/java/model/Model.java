package model;

import java.util.UUID;

/**
 * Stores the data of an object from the database or a new object created by the user
 */
public interface Model {
    /**
     * Gets the username of the owner of the model
     * @return String of the username
     */
    String getUsername();

    /**
     * Generates a uniqueID for a model
     * @return uniqueID that identifies a model
     */
    default String generateUniqueID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
