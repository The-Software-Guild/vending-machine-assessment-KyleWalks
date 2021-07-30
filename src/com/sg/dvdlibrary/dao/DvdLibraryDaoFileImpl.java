/*********************************
* The Software Guild
* Copyright (C) 2020 Wiley edu LLC - All Rights Reserved
*********************************/
package com.sg.dvdlibrary.dao;

/**
 * TSG Official Implementation of the UserIO interface
 * May your view be ever in your favor!
 * @author ahill
 */
import com.sg.dvdlibrary.dto.Dvd;
import com.sg.dvdlibrary.ui.UserIO;

import java.io.*;
import java.util.*;

public class DvdLibraryDaoFileImpl implements UserIO, DvdLibraryDao {

    final private Scanner console = new Scanner(System.in);

    private final Map<String, Dvd> dvds = new HashMap<>();

    public static final String LIBRARY_FILE = "library.txt";
    public static final String DELIMITER = "::";

    /**
     * Takes a string representing a DVD object and splits it on the DELIMITER
     * to obtain each property of the DVD.
     *
     * 1234::Ada::Lovelace::Java-September1842
     * __________________________________________________________________________
     * |              |    |              |                        |          | |
     * |The Green Mile|1999|Frank Darabont|Warner Hollywood Studios|10/10 Good|R|
     * |              |    |              |                        |          | |
     * --------------------------------------------------------------------------
     *
     * @param dvdAsText String representing a DVD object as text.
     * @return DVD loaded from database.
     */
    private Dvd unmarshallDvd(String dvdAsText){
        String[] dvdTokens = dvdAsText.split(DELIMITER);

        String dvdTitle = dvdTokens[0];

        Dvd dvdFromFile = new Dvd(dvdTitle);

        // Check if optional input was entered.
        if (!dvdTokens[1].equals(" "))
            dvdFromFile.setRelDate(dvdTokens[1]);

        if (!dvdTokens[2].equals(" "))
            dvdFromFile.setDirector(dvdTokens[2]);

        if (!dvdTokens[3].equals(" "))
            dvdFromFile.setStudio(dvdTokens[3]);

        if (!dvdTokens[4].equals(" "))
            dvdFromFile.setUserNote(dvdTokens[4]);

        if (!dvdTokens[5].equals(" "))
            dvdFromFile.setMpaaRating(dvdTokens[5]);

        return dvdFromFile;
    }

    /**
     * Converts a DVD object into a string where each property
     * is split by the DELIMITER.
     *
     * The Green Mile::1999::Frank Darabont::Warner Hollywood Studios::10/10 Good::R
     *
     * @param aDvd DVD object to be converted.
     * @return the string representing the DVD object.
     */
    private String marshallDvd(Dvd aDvd){

        String dvdAsText = aDvd.getTitle() + DELIMITER;

        dvdAsText += aDvd.getRelDate() + DELIMITER;

        dvdAsText += aDvd.getDirector() + DELIMITER;

        dvdAsText += aDvd.getStudio() + DELIMITER;

        dvdAsText += aDvd.getUserNote() + DELIMITER;

        dvdAsText += aDvd.getMpaaRating();

        return dvdAsText;
    }

    /**
     * Writes all DVDs in the library out to LIBRARY_FILE. See loadLibrary
     * for file format.
     *
     * @throws DvdLibraryDaoException if an error occurs writing to the file
     */
    private void writeLibrary() throws DvdLibraryDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new DvdLibraryDaoException(
                    "Could not save dvd data.", e);
        }

        String dvdAsText;
        List<Dvd> dvdList = this.getAllDvds();

        for (Dvd currentDvd : dvdList) {
            dvdAsText = marshallDvd(currentDvd);

            out.println(dvdAsText);

            out.flush();
        }

        out.close();
    }

    /**
     * Converts all the strings representing DVDs in the
     * library file into DVD objects.
     *
     * @throws DvdLibraryDaoException if loading the library file fails.
     */
    private void loadLibrary() throws DvdLibraryDaoException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(LIBRARY_FILE)));
        } catch (FileNotFoundException e) {
            throw new DvdLibraryDaoException(
                    "-_- Could not load roster data into memory.", e);
        }

        String currentLine;

        Dvd currentDvd;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();

            currentDvd = unmarshallDvd(currentLine);

            dvds.put(currentDvd.getTitle(), currentDvd);
        }

        scanner.close();
    }

    /**
     * Adds a DVD to the library file.
     *
     * @param dvdTitle title with which DVD is to be associated
     * @param dvd DVD to be added to the library
     * @return the DVD added to the library.
     * @throws DvdLibraryDaoException if loading/saving the library fails.
     */
    @Override
    public Dvd addDvd(String dvdTitle, Dvd dvd) throws DvdLibraryDaoException {
        loadLibrary();

        Dvd newDvd = dvds.put(dvdTitle, dvd);

        writeLibrary();

        return newDvd;
    }

    /**
     * Loads all the DVDs from the library.
     *
     * @return the list of DVDs
     * @throws DvdLibraryDaoException if loading the library fails.
     */
    @Override
    public List<Dvd> getAllDvds() throws DvdLibraryDaoException {
        loadLibrary();
        return new ArrayList(dvds.values());
    }

    /**
     * Retrieves a DVD from the database.
     *
     * @param dvdTitle Title of the DVD to retrieve
     * @return the DVD retrieved
     * @throws DvdLibraryDaoException if loading the library fails.
     */
    @Override
    public Dvd getDvd(String dvdTitle) throws DvdLibraryDaoException {
        loadLibrary();
        return dvds.get(dvdTitle);
    }

    /**
     * Removes a DVD from the library.
     *
     * @param dvdTitle title of DVD to be removed
     * @return the removed DVD
     * @throws DvdLibraryDaoException if loading/saving the library fails.
     */
    @Override
    public Dvd removeDvd(String dvdTitle) throws DvdLibraryDaoException {
        loadLibrary();

        Dvd removedDvd = dvds.remove(dvdTitle);

        writeLibrary();

        return removedDvd;
    }

    /**
     *
     * A very simple method that takes in a message to display on the console 
     * and then waits for a integer answer from the user to return.
     *
     * @param msg - String of information to display to the user.
     *
     */
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and then waits for an answer from the user to return.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as string
     */
    @Override
    public String readString(String msgPrompt) {
        System.out.println(msgPrompt);
        return console.nextLine();
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter an integer
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as integer
     */
    @Override
    public int readInt(String msgPrompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // print the message msgPrompt (ex: asking for the # of cats!)
                String stringValue = this.readString(msgPrompt);
                // Get the input line, and try and parse
                num = Integer.parseInt(stringValue); // if it's 'bob' it'll break
                invalidInput = false; // or you can use 'break;'
            } catch (NumberFormatException e) {
                // If it explodes, it'll go here and do this.
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter an integer
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an integer value as an answer to the message prompt within the min/max range
     */
    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a long
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as long
     */
    @Override
    public long readLong(String msgPrompt) {
        while (true) {
            try {
                return Long.parseLong(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a double
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an long value as an answer to the message prompt within the min/max range
     */
    @Override
    public long readLong(String msgPrompt, long min, long max) {
        long result;
        do {
            result = readLong(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a float
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as float
     */
    @Override
    public float readFloat(String msgPrompt) {
        while (true) {
            try {
                return Float.parseFloat(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a float
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an float value as an answer to the message prompt within the min/max range
     */
    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a double
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as double
     */
    @Override
    public double readDouble(String msgPrompt) {
        while (true) {
            try {
                return Double.parseDouble(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a double
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an double value as an answer to the message prompt within the min/max range
     */
    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
    }

}
