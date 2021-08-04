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
import java.math.BigDecimal;
import java.util.*;

public class VendingMachineDaoFileImpl implements UserIO, VendingMachineDao {

    final private Scanner console = new Scanner(System.in);

    private final Map<String, VendingItem> vendingItems = new HashMap<>();

    public static final String VENDING_MACHINE_TXT = "vendingMachine.txt";
    public static final String DELIMITER = "::";

    /**
     * Takes a string representing a VendingItem object and splits it on the DELIMITER
     * to obtain each property of the VendingItem.
     *
     * Doritos::2.32::5
     * ________________
     * |       |    | |
     * |Doritos|2.32|5|
     * |       |    | |
     * ----------------
     *
     * @param itemAsText String representing a VendingItem object as text.
     * @return VendingItem loaded from database.
     */
    private VendingItem unmarshallItem(String itemAsText){
        String[] itemTokens = itemAsText.split(DELIMITER);

        String itemName = itemTokens[0];

        VendingItem vendingItemFromFile = new VendingItem(itemName);

        vendingItemFromFile.setPrice(new BigDecimal(itemTokens[1]));

        vendingItemFromFile.setCount(Integer.parseInt(itemTokens[2]));

        return vendingItemFromFile;
    }

    /**
     * Converts a VendingItem object into a string where each property
     * is split by the DELIMITER.
     *
     * Doritos::2.32::5
     *
     * @param aVendingItem VendingItem object to be converted.
     * @return the string representing the VendingItem object.
     */
    private String marshallItem(VendingItem aVendingItem){

        String itemAsText = aVendingItem.getName() + DELIMITER;

        itemAsText += aVendingItem.getPrice() + DELIMITER;

        itemAsText += aVendingItem.getCount();

        return itemAsText;
    }

    /**
     * Writes all VendingItems in the database out to VENDING_MACHINE.
     *
     * @throws VendingMachineDaoException if an error occurs writing to the file
     */
    private void writeVendingMachine() throws VendingMachineDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(VENDING_MACHINE_TXT));
        } catch (IOException e) {
            throw new VendingMachineDaoException(
                    "Could not save vending machine data.", e);
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
     * Converts all the strings representing VendingItems in the
     * database file into VendingItem objects.
     *
     * @throws VendingMachineDaoException if loading the database file fails.
     */
    private void loadVendingMachine() throws VendingMachineDaoException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(VENDING_MACHINE_TXT)));
        } catch (FileNotFoundException e) {
            throw new VendingMachineDaoException(
                    "Could not load vending machine data into memory.", e);
        }

        String currentLine;

        VendingItem currentVendingItem;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();

            currentVendingItem = unmarshallItem(currentLine);

            vendingItems.put(currentVendingItem.getName(), currentVendingItem);
        }

        scanner.close();
    }

    /**
     * Loads all the VendingItems from the database.
     *
     * @return the list of VendingItems
     * @throws VendingMachineDaoException if loading the database fails.
     */
    @Override
    public List<VendingItem> getAllItems() throws VendingMachineDaoException {
        loadVendingMachine();
        return new ArrayList(vendingItems.values());
    }

    /**
     * Retrieves a VendingItem from the database.
     *
     * @param itemName name of the VendingItem to retrieve
     * @return the VendingItem retrieved
     * @throws VendingMachineDaoException if loading the database fails.
     */
    @Override
    public VendingItem getItem(String itemName) throws VendingMachineDaoException {
        loadVendingMachine();
        return vendingItems.get(itemName);
    }

    /**
     * Removes a VendingItem from the database.
     *
     * @param itemName name of VendingItem to be removed
     * @return the removed VendingItem
     * @throws VendingMachineDaoException if loading/saving the database fails.
     */
    @Override
    public VendingItem dispenseItem(String itemName) throws VendingMachineDaoException {
        loadVendingMachine();

        VendingItem dispensedVendingItem = vendingItems.get(itemName);
        if (dispensedVendingItem != null) {
            if (dispensedVendingItem.getCount() != 0) {
                dispensedVendingItem.setCount(dispensedVendingItem.getCount() - 1);
                vendingItems.put(itemName, dispensedVendingItem);
            }
        }

        writeVendingMachine();

        return dispensedVendingItem;
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
     * and continually reprompts the user with that message until they enter a BigDecimal
     * to be returned as the answer to that message.
     *
     * @param prompt - String explaining what information you want from the user.
     * @return the answer to the message as BigDecimal
     */
    @Override
    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            try {
                return new BigDecimal(this.readString(prompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console,
     * and continually reprompts the user with that message until they enter a BigDecimal
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param prompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @return a BigDecimal value as an answer to the message prompt within the min/max range
     */
    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min) {
        BigDecimal result;

        do {
            result = readBigDecimal(prompt);
        } while (result.compareTo(min) < 0);

        return result;
    }

}
