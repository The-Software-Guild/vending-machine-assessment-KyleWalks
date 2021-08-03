package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.VendingItem;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineView {

    private final UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    /**
     * Display the main menu of the application.
     *
     * @return the user menu choice as an integer.
     */
    public int printMenuAndGetSelection() {

        io.print("Main Menu");
        io.print("1. List vending items");
        io.print("2. View a vending item");
        io.print("3. Create New vending item");
        io.print("4. Remove a vending item");
        io.print("5. Edit a vending item");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public BigDecimal getCurrency() {
        BigDecimal currency;

        currency = io.readBigDecimal("Please enter a sum of money.", new BigDecimal(0));

        return currency;
    }

    /**
     * Displays a compact summary of the vending items
     * in the library.
     *
     * @param vendingItemList List of vending items in the library.
     */
    public void displayItemList(List<VendingItem> vendingItemList) {
        for (VendingItem currentVendingItem : vendingItemList) {
            System.out.printf("%10s: %-15s\n", "Name", currentVendingItem.getName());
            System.out.printf("%10s: $%-15s\n", "Price", currentVendingItem.getPrice());
            System.out.printf("%10s: #%-15s\n", "Count", currentVendingItem.getCount());

            io.print("");
        }
    }

    public void displayChangeResult(BigDecimal change) {
        io.print("Your change is: $" + change);
    }

    public String getItemNameChoice() {
        return io.readString("Please enter the vending item name.");
    }

    /**
     * Formatted output of the properties of a vending item.
     *
     * @param vendingItem the vending item to be displayed.
     */
    public void displayItem(VendingItem vendingItem) {
        io.print("");

        if (vendingItem != null) {
            System.out.printf("%20s: %-15s\n", "Name", vendingItem.getName());
            System.out.printf("%20s: %-15s\n", "Price", vendingItem.getPrice());
            System.out.printf("%20s: %-15s\n", "Count", vendingItem.getCount());

            io.print("");
        } else {
            io.print("No such vending item.");
        }

        io.readString("Please hit enter to continue.");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayExitBanner() {
        io.print("Closing.");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayDisplayAllBanner() {
        io.print("=== Display All vending items ===");
    }

    public void displayEnterMoneyBanner() {
        io.print("=== Enter Sum of Money ===");
    }

    /**
     * Checks that a string contains at least one letter or number.
     *
     * @param s The string to be checked
     * @return true if a letter or number was found.
     */
    private boolean checkString(String s) {
        if (s == null)
            return false;

        boolean charFound = false;

        int index = 0;

        while (index < s.length() && !charFound) {
            if (Character.isLetterOrDigit(s.charAt(index)))
                charFound = true;

            index++;
        }

        return charFound;
    }
}
