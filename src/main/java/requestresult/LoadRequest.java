package requestresult;

import model.*;

/** Serialized HTTP load request */
public class LoadRequest {
    /** List of Users */
    private User[] users;
    /** List of Persons */
    private Person[] persons;
    /** List of Events */
    private Event[] events;

    /**
     * Creates a LoadRequest
     * @param users List of Users
     * @param persons List of Persons
     * @param events List of Events
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}
