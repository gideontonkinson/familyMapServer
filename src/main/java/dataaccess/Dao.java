package dataaccess;

import model.Model;

/**
 * Interface to access information stored in the database
 * */
public interface Dao{

    /**
     * Gets data from the database and creates an object related to the selected table
     * @param id
     * @return The Model object created from the data in the database
     * @throws DaoException if there is an error in accessing the database and it's tables
     */
    Model getFromDB(String id) throws DaoException;

    /**
     * Adds a Model and it's data to the database
     * @param model
     * @throws DaoException if there was an error in adding a Model to the database
     */
    boolean addToDB(Model model) throws DaoException;

    /**
     * Clears the respective table
     * @throws DaoException
     */
    void clear() throws DaoException;
}
