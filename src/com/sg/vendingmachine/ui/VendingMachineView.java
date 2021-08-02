package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.VendingItem;

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
        io.print("1. List DVDs");
        io.print("2. View a DVD");
        io.print("3. Create New DVD");
        io.print("4. Remove a DVD");
        io.print("5. Edit a DVD");
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
        io.print("1. Title");
        io.print("2. Release Date");
        io.print("3. Director");
        io.print("4. Studio");
        io.print("5. Note");
        io.print("6. MPAA Rating");
        io.print("7. Exit Menu");

        return io.readInt("Please select from the above choices.", 1, 7);
    }

    /**
     * Retrieves the properties of the DVD being
     * created.
     *
     * @return the DVD that was created.
     */
    public VendingItem getNewItemInfo() {
        // Required
        String itemName = io.readString("Please enter DVD title");
        while (!checkString(itemName))
            itemName = io.readString("Please enter DVD title");

        // Optional
        String relDate = io.readString("[Optional]Please enter the release date(YYYY)");
        String director = io.readString("[Optional]Please enter the director's name");
        String studio = io.readString("[Optional]Please enter the studio");
        String userEntry = io.readString("[Optional]Please enter your rating/note");
        String mpaaRating = io.readString("[Optional]Please enter the MPAA Rating");

        // Check for empty optional input.
        relDate = !relDate.isEmpty() ? relDate : " ";
        director = !director.isEmpty() ? relDate : " ";
        studio = !studio.isEmpty() ? relDate : " ";
        userEntry = !userEntry.isEmpty() ? relDate : " ";
        mpaaRating = !mpaaRating.isEmpty() ? relDate : " ";

        VendingItem currentVendingItem = new VendingItem(itemName);

        currentVendingItem.setRelDate(relDate);
        currentVendingItem.setDirector(director);
        currentVendingItem.setStudio(studio);
        currentVendingItem.setUserNote(userEntry);
        currentVendingItem.setMpaaRating(mpaaRating);

        return currentVendingItem;
    }

    /**
     * Retrieves the property value being changed by
     * the user.
     *
     * @param currVendingItem The DVD that is being edited.
     * @param propChoice Integer representing the property to be changed.
     * @return the altered DVD.
     */
    public VendingItem getNewItemInfo(VendingItem currVendingItem, int propChoice) {
        String propChange = "";

        while (!checkString(propChange)) {
            switch (propChoice) {
                case 1:
                    propChange = io.readString("Please enter DVD title");
                    break;
                case 2:
                    propChange = io.readString("Please enter the release date(YYYY)");
                    break;
                case 3:
                    propChange = io.readString("Please enter the director's name");
                    break;
                case 4:
                    propChange = io.readString("Please enter the studio");
                    break;
                case 5:
                    propChange = io.readString("Please enter your rating/note");
                    break;
                default:
                    return currVendingItem;
            }
        }

        switch (propChoice) {
            case 1:
                currVendingItem.setTitle(propChange);
                break;
            case 2:
                currVendingItem.setRelDate(propChange);
                break;
            case 3:
                currVendingItem.setDirector(propChange);
                break;
            case 4:
                currVendingItem.setStudio(propChange);
                break;
            case 5:
                currVendingItem.setUserNote(propChange);
                break;
            default:
                break;
        }

        return currVendingItem;
    }

    public void displayCreateItemBanner() {
        io.print("=== Create DVD ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString("DVD successfully created.  Please hit enter to continue");
    }

    /**
     * Displays a compact summary of the DVDs
     * in the library.
     *
     * @param vendingItemList List of DVDs in the library.
     */
    public void displayItemList(List<VendingItem> vendingItemList) {
        for (VendingItem currentVendingItem : vendingItemList) {
            String dvdInfo = String.format("%s : %s %s",
                    currentVendingItem.getTitle(),
                    currentVendingItem.getRelDate(),
                    currentVendingItem.getDirector());

            io.print(dvdInfo);
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayRemoveItemBanner() {
        io.print("=== Remove DVD ===");
    }

    public void displayRemoveResult(VendingItem vendingItemRecord) {
        if(vendingItemRecord != null){
            io.print("DVD successfully removed.");
        }else{
            io.print("No such DVD.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayDisplayItemBanner() {
        io.print("=== Display DVD ===");
    }

    public String getItemNameChoice() {
        return io.readString("Please enter the DVD title.");
    }

    public String getItemEditChoice() {
        io.print("");
        return io.readString("Please enter the DVD property you would like to edit.");
    }

    /**
     * Formatted output of the properties of a DVD.
     *
     * @param vendingItem the DVD to be displayed.
     */
    public void displayItem(VendingItem vendingItem) {
        io.print("");

        if (vendingItem != null) {
            System.out.printf("%20s: %-15s\n", "Title", vendingItem.getTitle());
            System.out.printf("%20s: %-15s\n", "Release Date", vendingItem.getRelDate());
            System.out.printf("%20s: %-15s\n", "Director", vendingItem.getDirector());
            System.out.printf("%20s: %-15s\n", "Studio", vendingItem.getStudio());
            System.out.printf("%20s: %-15s\n", "User Note", vendingItem.getUserNote());
            System.out.printf("%20s: %-15s\n", "MPAA Rating", vendingItem.getMpaaRating());

            io.print("");
        } else {
            io.print("No such DVD.");
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
        io.print("=== Display All DVDs ===");
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
