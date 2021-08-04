package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.VendingItem;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineView {

    private final UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public BigDecimal getCurrency() {
        BigDecimal currency;

        currency = io.readBigDecimal("Please enter a sum of money (0 to exit).", new BigDecimal("0"));

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

    public void displayChangeResult(Change change, int[] changeAsCoins) {
        String out = String.format("Your change is: $%s\n%5d Quarter(s) %5d Dime(s) %5d Nickel(s) %5d Penny(s)",
                change.getChange(),
                changeAsCoins[0],
                changeAsCoins[1],
                changeAsCoins[2],
                changeAsCoins[3]);

        io.print(out);

        io.readString("\nPress enter to continue.");
    }

    public String getItemNameChoice() {
        return io.readString("Please enter the vending item name (0 to exit).");
    }

    /**
     * Formatted output of the properties of a vending item.
     *
     * @param vendingItem the vending item to be displayed.
     */
    public void displayItem(VendingItem vendingItem) {
        if (vendingItem != null) {
            System.out.printf("%20s: %-15s\n", "Name", vendingItem.getName());
            System.out.printf("%20s: %-15s\n", "Price", vendingItem.getPrice());
            System.out.printf("%20s: %-15s\n", "Count", vendingItem.getCount());

            io.print("");
        } else {
            io.print("No such vending item.\n");
        }
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("\n=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayExitBanner() {
        io.print("\nClosing.");
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
