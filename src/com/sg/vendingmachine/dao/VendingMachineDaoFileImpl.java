/*********************************
* The Software Guild
* Copyright (C) 2020 Wiley edu LLC - All Rights Reserved
*********************************/
package com.sg.vendingmachine.dao;

/**
 * TSG Official Implementation of the UserIO interface
 * May your view be ever in your favor!
 * @author ahill
 */
import com.sg.vendingmachine.dto.VendingItem;
import com.sg.vendingmachine.ui.UserIO;

import java.io.*;
import java.util.*;

public class VendingMachineDaoFileImpl implements UserIO, VendingMachineDao {

    final private Scanner console = new Scanner(System.in);

    private final Map<String, VendingItem> vendingItems = new HashMap<>();

    public static final String VENDING_MACHINE_TXT = "vendingMachine.txt";
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
     * @param itemAsText String representing a DVD object as text.
     * @return DVD loaded from database.
     */
    private VendingItem unmarshallItem(String itemAsText){
        String[] itemTokens = itemAsText.split(DELIMITER);

        String itemName = itemTokens[0];

        VendingItem vendingItemFromFile = new VendingItem(itemName);

        // Check if optional input was entered.
        if (!itemTokens[1].equals(" "))
            vendingItemFromFile.setRelDate(itemTokens[1]);

        if (!itemTokens[2].equals(" "))
            vendingItemFromFile.setDirector(itemTokens[2]);

        if (!itemTokens[3].equals(" "))
            vendingItemFromFile.setStudio(itemTokens[3]);

        if (!itemTokens[4].equals(" "))
            vendingItemFromFile.setUserNote(itemTokens[4]);

        if (!itemTokens[5].equals(" "))
            vendingItemFromFile.setMpaaRating(itemTokens[5]);

        return vendingItemFromFile;
    }

    /**
     * Converts a DVD object into a string where each property
     * is split by the DELIMITER.
     *
     * The Green Mile::1999::Frank Darabont::Warner Hollywood Studios::10/10 Good::R
     *
     * @param aVendingItem DVD object to be converted.
     * @return the string representing the DVD object.
     */
    private String marshallItem(VendingItem aVendingItem){

        String itemAsText = aVendingItem.getTitle() + DELIMITER;

        itemAsText += aVendingItem.getRelDate() + DELIMITER;

        itemAsText += aVendingItem.getDirector() + DELIMITER;

        itemAsText += aVendingItem.getStudio() + DELIMITER;

        itemAsText += aVendingItem.getUserNote() + DELIMITER;

        itemAsText += aVendingItem.getMpaaRating();

        return itemAsText;
    }

    /**
     * Writes all DVDs in the library out to LIBRARY_FILE. See loadLibrary
     * for file format.
     *
     * @throws VendingMachineDaoException if an error occurs writing to the file
     */
    private void writeVendingMachine() throws VendingMachineDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(VENDING_MACHINE_TXT));
        } catch (IOException e) {
            throw new VendingMachineDaoException(
                    "Could not save dvd data.", e);
        }

        String itemAsText;
        List<VendingItem> vendingItemList = this.getAllItems();

        for (VendingItem currentVendingItem : vendingItemList) {
            itemAsText = marshallItem(currentVendingItem);

            out.println(itemAsText);

            out.flush();
        }

        out.close();
    }

    /**
     * Converts all the strings representing DVDs in the
     * library file into DVD objects.
     *
     * @throws VendingMachineDaoException if loading the library file fails.
     */
    private void loadVendingMachine() throws VendingMachineDaoException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(VENDING_MACHINE_TXT)));
        } catch (FileNotFoundException e) {
            throw new VendingMachineDaoException(
                    "-_- Could not load vending machine data into memory.", e);
        }

        String currentLine;

        VendingItem currentVendingItem;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();

            currentVendingItem = unmarshallItem(currentLine);

            vendingItems.put(currentVendingItem.getItemName(), currentVendingItem);
        }

        scanner.close();
    }

    /**
     * Adds a DVD to the library file.
     *
     * @param itemName title with which DVD is to be associated
     * @param vendingItem DVD to be added to the library
     * @return the DVD added to the library.
     * @throws VendingMachineDaoException if loading/saving the library fails.
     */
    @Override
    public VendingItem addItem(String itemName, VendingItem vendingItem) throws VendingMachineDaoException {
        loadVendingMachine();

        VendingItem newVendingItem = vendingItems.put(itemName, vendingItem);

        writeVendingMachine();

        return newVendingItem;
    }

    /**
     * Loads all the DVDs from the library.
     *
     * @return the list of DVDs
     * @throws VendingMachineDaoException if loading the library fails.
     */
    @Override
    public List<VendingItem> getAllItems() throws VendingMachineDaoException {
        loadVendingMachine();
        return new ArrayList(vendingItems.values());
    }

    /**
     * Retrieves a DVD from the database.
     *
     * @param itemName Title of the DVD to retrieve
     * @return the DVD retrieved
     * @throws VendingMachineDaoException if loading the library fails.
     */
    @Override
    public VendingItem getItem(String itemName) throws VendingMachineDaoException {
        loadVendingMachine();
        return vendingItems.get(itemName);
    }

    /**
     * Removes a DVD from the library.
     *
     * @param itemName title of DVD to be removed
     * @return the removed DVD
     * @throws VendingMachineDaoException if loading/saving the library fails.
     */
    @Override
    public VendingItem removeItem(String itemName) throws VendingMachineDaoException {
        loadVendingMachine();

        VendingItem removedVendingItem = vendingItems.remove(itemName);

        writeVendingMachine();

        return removedVendingItem;
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
