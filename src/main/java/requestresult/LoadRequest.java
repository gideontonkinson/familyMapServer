package requestresult;

import model.*;

import java.util.ArrayList;

public class LoadRequest {
    private ArrayList<User> users;
    private ArrayList<Person> persons;
    private ArrayList <Event> events;

    /**
     * Creates a LoadRequest
     * @param users
     * @param persons
     * @param events
     */
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
