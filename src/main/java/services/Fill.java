package services;

import com.google.gson.Gson;
import dataaccess.*;
import model.Event;
import model.Person;
import model.User;
import requestresult.FillResult;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

/** Services an API request to fill data for a user in the database */
public class Fill {
    /** Connection to the family map database */
    private static Database db = new Database();
    /** Randomizer to select random names or locations */
    private final Random rand = new Random();
    /** DAO to interact with the Events table */
    private static EventDao eventDao;
    /** DAO to interact with the Person table */
    private static PersonDao personDao;
    /** DAO to interact with the User table */
    private static UserDao userDao;
    /** Holds a list of male first name strings */
    private StringData fnames;
    /** Holds a list of female first name strings */
    private StringData mnames;
    /** Holds a list of surname name strings */
    private StringData snames;
    /** Holds a list of location objects */
    private LocationData locations;

    /**
     * Holds the information for a location
     */
    class Location {
        /** Country name */
        String country;
        /** City name */
        String city;
        /** Latitude Information */
        float latitude;
        /** Longitude Information */
        float longitude;

        /**
         * Creates a new location
         * @param country
         * @param city
         * @param latitude
         * @param longitude
         */
        public Location(String country, String city, float latitude, float longitude) {
            this.country = country;
            this.city = city;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    /**
     * Stores a list of strings (made to convert json data)
     */
    class StringData {
        /** String data */
        ArrayList<String> data;

        /**
         * Creates StringData object
         * @param data String ArrayList
         */
        public StringData(ArrayList<String> data) {
            this.data = data;
        }

        public ArrayList<String> getData() {
            return data;
        }
    }

    /**
     * Stores a list of locations (made to covert json data)
     */
    class LocationData {
        /** Location data */
        ArrayList<Location> data;

        /**
         * Creates a LocationData
         * @param data Location ArrayList
         */
        public LocationData(ArrayList<Location> data) {
            this.data = data;
        }

        public ArrayList<Location> getData() {
            return data;
        }
    }

    /**
     * Creates an Fill Service Object and serializes from the json files
     */
    public Fill() {
        Gson gson = new Gson();
        try {
            Reader fnamesStream = new FileReader("json/fnames.json");
            Reader mnamesStream = new FileReader("json/mnames.json");
            Reader snamesStream = new FileReader("json/snames.json");
            Reader locationsStream = new FileReader("json/locations.json");
            fnames = gson.fromJson(fnamesStream, (Type) StringData.class);
            mnames = gson.fromJson(mnamesStream, (Type) StringData.class);
            snames = gson.fromJson(snamesStream, (Type) StringData.class);
            locations = gson.fromJson(locationsStream, (Type) LocationData.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills a user with generated data
     * @return FillResult if successful
     */
    public FillResult fill(String username, int generations) {
        FillResult result;
        boolean commit = false;
        try {
            db.openConnection();
            eventDao = new EventDao(db.getConnection());
            personDao = new PersonDao(db.getConnection());
            userDao = new UserDao(db.getConnection());
            User u  = userDao.getFromDB(username);
            if(u == null){
                return new FillResult("Error: User does not exist", false);
            }
            Person p = generatePerson(username, u.getGender(), generations, 2000, true);
            try {
                personDao.addToDB(p);
            } catch (DaoException e){
                e.printStackTrace();
            }
            int numPeople = (int)(Math.pow(2, generations + 1) - 1);
            int numEvents = numPeople * 3 - 2;
            String successfulMessage = "Successfully added " + numPeople + " persons and " + numEvents + " events.";
            result = new FillResult(successfulMessage);
            commit = true;
        } catch (DaoException e) {
            e.printStackTrace();
            result = new FillResult(e.getMessage(), false);
        } finally {
            try {
                db.closeConnection(commit);
            } catch (DaoException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Sets up the DAOs and database
     * @throws DaoException if there is an error with the DAOs
     */
    public void setUp() throws DaoException {
        db.openConnection();
        eventDao = new EventDao(db.getConnection());
        personDao = new PersonDao(db.getConnection());
        userDao = new UserDao(db.getConnection());
    }

    /**
     * Closes the connection to the database
     * @throws DaoException if there is an error with the DAOs
     */
    public void cleanUp() throws DaoException {
        db.closeConnection(true);
    }

    /**
     * Generates a person and their ancestors with semi-random information
     * @param username Username to fill data for
     * @param gender Gender of the person
     * @param generations Number of generations left to fail
     * @param birthYear Birth year of person
     * @param root Boolean, true if the person is the root and won't have a death date
     * @return Person p which is the user base object
     * @throws DaoException if there is an error with the DAOs
     */
    public Person generatePerson(String username, String gender, int generations, int birthYear, boolean root) throws DaoException {
        Person mother = null;
        Person father = null;
        if(generations >= 1) {
            int motherBirthYear = generateParentBirthYear(birthYear);
            int fatherBirthYear = generateParentBirthYear(birthYear);
            int marriageYear;
            if(motherBirthYear < fatherBirthYear){
                marriageYear = generateParentMarriageYear(fatherBirthYear);
            } else {
                marriageYear = generateParentMarriageYear(motherBirthYear);
            }
            mother = generatePerson(username, "f", generations - 1, motherBirthYear, false);
            father = generatePerson(username, "m", generations - 1, fatherBirthYear, false);
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());
            personDao.addToDB(mother);
            personDao.addToDB(father);
            Location location = locations.getData().get(rand.nextInt(locations.getData().size()));
            Event motherMarriage = new Event(username, mother.getPersonID(), location.latitude, location.longitude,
                                        location.country, location.city, "Marriage", marriageYear);
            Event fatherMarriage = new Event(username, father.getPersonID(), location.latitude, location.longitude,
                                        location.country, location.city, "Marriage", marriageYear);
            eventDao.addToDB(motherMarriage);
            eventDao.addToDB(fatherMarriage);
        }

        String firstName;
        if(gender.equalsIgnoreCase("f")){
            firstName = fnames.getData().get(rand.nextInt(fnames.getData().size()));
        } else {
            firstName = mnames.getData().get(rand.nextInt(mnames.getData().size()));
        }
        String lastName = snames.getData().get(rand.nextInt(snames.getData().size()));
        Person person;

        if(root){
            User user = userDao.getFromDB(username);
            if(mother == null && father == null){
                person = new Person(user.getPersonID(), username, user.getFirstName(), user.getLastName(),
                        user.getGender());
            } else {
                person = new Person(user.getPersonID(), username, user.getFirstName(), user.getLastName(),
                        user.getGender(), father.getPersonID(), mother.getPersonID(), null);
            }
        } else {
            if(mother == null && father == null){
                person = new Person(username, firstName, lastName, gender);
            } else {
                person = new Person(username, firstName, lastName, gender,
                        father.getPersonID(), mother.getPersonID(), null);
            }
        }
        generateEvents(username, person.getPersonID(), birthYear, root);

        return person;
    }

    /**
     * Generates a realistic marriage year for the parents
     * @param youngerParentBirthYear Birth year of the younger parent
     * @return int of the marriage year
     */
    private int generateParentMarriageYear(int youngerParentBirthYear){
        int marriageYear = youngerParentBirthYear + 13;
        marriageYear += Math.random() * 30;
        return marriageYear;
    }

    /**
     * Generates a realistic age for the parents
     * @param childBirthYear Birth year of the child of the person
     * @return int of the parent birth year
     */
    private int generateParentBirthYear(int childBirthYear){
        int parentBirthYear = childBirthYear - 13;
        parentBirthYear -= Math.random() * 37;
        return parentBirthYear;
    }

    /**
     * Generates a realistic death year for a person
     * @param birthYear Birth year of person
     * @return int of the person death year
     */
    private int generateDeathYear(int birthYear){
        int deathYear = birthYear + 51;
        deathYear += Math.random() * 69;
        return deathYear;
    }

    /**
     * Generates events for a person
     * @param username Username to fill data for
     * @param personID ID of person to generate events for
     * @param birthYear Birth year of the person
     * @param root Boolean, true if the person is the root and won't have a death date
     * @throws DaoException if there is an error with the DAOs
     */
    private void generateEvents(String username, String personID, int birthYear, boolean root) throws DaoException {
        Location birthLocation = locations.getData().get(rand.nextInt(locations.getData().size()));
        Event birth = new Event(username, personID, birthLocation.latitude, birthLocation.longitude,
                birthLocation.country, birthLocation.city, "Birth", birthYear);
        eventDao.addToDB(birth);
        if(!root){
            Location deathLocation = locations.getData().get(rand.nextInt(locations.getData().size()));
            Event death = new Event(username, personID, deathLocation.latitude, deathLocation.longitude,
                    deathLocation.country, deathLocation.city, "Death", generateDeathYear(birthYear));
            eventDao.addToDB(death);
        }
    }
}
