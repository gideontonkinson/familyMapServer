package requestresult;

import model.*;

public class LoadRequest {
    private User[] users;
    private Person[] persons;
    private Event[] events;

    /**
     * Creates a LoadRequest
     * @param users
     * @param persons
     * @param events
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
