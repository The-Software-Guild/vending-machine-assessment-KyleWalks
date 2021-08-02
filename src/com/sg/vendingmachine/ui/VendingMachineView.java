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

    /**
     * Displays the menu used to choose which property
     * is to be edited by the user.
     *
     * @return the user choice as an integer.
     */
    public int printEditMenuAndGetSelection() {

        io.print("Edit Menu");
        io.print("1. Name");
        io.print("2. Price");
        io.print("3. Count");
        io.print("4. Exit Menu");

        return io.readInt("Please select from the above choices.", 1, 4);
    }

    /**
     * Retrieves the properties of the vending item being
     * created.
     *
     * @return the vending item that was created.
     */
    public VendingItem getNewItemInfo() {
        // Required
        String itemName = io.readString("Please enter vending item name");
        while (!checkString(itemName))
            itemName = io.readString("Please enter vending item name");

        BigDecimal price = io.readBigDecimal("Please enter the price");
        int count = io.readInt("Please enter the item count");

        VendingItem currentVendingItem = new VendingItem(itemName);

        currentVendingItem.setPrice(price);
        currentVendingItem.setCount(count);

        return currentVendingItem;
    }

    /**
     * Retrieves the property value being changed by
     * the user.
     *
     * @param currVendingItem The vending item that is being edited.
     * @param propChoice Integer representing the property to be changed.
     * @return the altered vending item.
     */
    public VendingItem getNewItemInfo(VendingItem currVendingItem, int propChoice) {
        String propChange = "";

        while (!checkString(propChange)) {
            switch (propChoice) {
                case 1:
                    propChange = io.readString("Please enter vending item name");
                    break;
                case 2:
                    propChange = io.readString("Please enter the price");
                    break;
                case 3:
                    propChange = io.readString("Please enter the item count");
                    break;
                default:
                    return currVendingItem;
            }
        }

        switch (propChoice) {
            case 1:
                currVendingItem.setName(propChange);
                break;
            case 2:
                currVendingItem.setPrice(new BigDecimal(propChange));
                break;
            case 3:
                currVendingItem.setCount(Integer.parseInt(propChange));
                break;
            default:
                break;
        }

        return currVendingItem;
    }

    public void displayCreateItemBanner() {
        io.print("=== Create vending item ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString("vending item successfully created.  Please hit enter to continue");
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
        io.readString("Please hit enter to continue.");
    }

    public void displayRemoveItemBanner() {
        io.print("=== Remove vending item ===");
    }

    public void displayRemoveResult(VendingItem vendingItemRecord) {
        if(vendingItemRecord != null){
            io.print("vending item successfully removed.");
        }else{
            io.print("No such vending item.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayDisplayItemBanner() {
        io.print("=== Display vending item ===");
    }

    public String getItemNameChoice() {
        return io.readString("Please enter the vending item name.");
    }

    public String getItemEditChoice() {
        io.print("");
        return io.readString("Please enter the vending item property you would like to edit.");
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
