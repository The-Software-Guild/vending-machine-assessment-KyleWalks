package com.sg.dvdlibrary.ui;

import com.sg.dvdlibrary.dto.Dvd;

import java.util.List;

public class DvdLibraryView {

    private final UserIO io;

    public DvdLibraryView(UserIO io) {
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
    public Dvd getNewDvdInfo() {
        // Required
        String dvdTitle = io.readString("Please enter DVD title");
        while (!checkString(dvdTitle))
            dvdTitle = io.readString("Please enter DVD title");

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

        Dvd currentDvd = new Dvd(dvdTitle);

        currentDvd.setRelDate(relDate);
        currentDvd.setDirector(director);
        currentDvd.setStudio(studio);
        currentDvd.setUserNote(userEntry);
        currentDvd.setMpaaRating(mpaaRating);

        return currentDvd;
    }

    /**
     * Retrieves the property value being changed by
     * the user.
     *
     * @param currDvd The DVD that is being edited.
     * @param propChoice Integer representing the property to be changed.
     * @return the altered DVD.
     */
    public Dvd getNewDvdInfo(Dvd currDvd, int propChoice) {
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
                    return currDvd;
            }
        }

        switch (propChoice) {
            case 1:
                currDvd.setTitle(propChange);
                break;
            case 2:
                currDvd.setRelDate(propChange);
                break;
            case 3:
                currDvd.setDirector(propChange);
                break;
            case 4:
                currDvd.setStudio(propChange);
                break;
            case 5:
                currDvd.setUserNote(propChange);
                break;
            default:
                break;
        }

        return currDvd;
    }

    public void displayCreateDvdBanner() {
        io.print("=== Create DVD ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString("DVD successfully created.  Please hit enter to continue");
    }

    /**
     * Displays a compact summary of the DVDs
     * in the library.
     *
     * @param dvdList List of DVDs in the library.
     */
    public void displayDvdList(List<Dvd> dvdList) {
        for (Dvd currentDvd : dvdList) {
            String dvdInfo = String.format("%s : %s %s",
                    currentDvd.getTitle(),
                    currentDvd.getRelDate(),
                    currentDvd.getDirector());

            io.print(dvdInfo);
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayRemoveDvdBanner() {
        io.print("=== Remove DVD ===");
    }

    public void displayRemoveResult(Dvd dvdRecord) {
        if(dvdRecord != null){
            io.print("DVD successfully removed.");
        }else{
            io.print("No such DVD.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayDisplayDvdBanner() {
        io.print("=== Display DVD ===");
    }

    public String getDvdTitleChoice() {
        return io.readString("Please enter the DVD title.");
    }

    public String getDvdEditChoice() {
        io.print("");
        return io.readString("Please enter the DVD property you would like to edit.");
    }

    /**
     * Formatted output of the properties of a DVD.
     *
     * @param dvd the DVD to be displayed.
     */
    public void displayDvd(Dvd dvd) {
        io.print("");

        if (dvd != null) {
            System.out.printf("%20s: %-15s\n", "Title", dvd.getTitle());
            System.out.printf("%20s: %-15s\n", "Release Date", dvd.getRelDate());
            System.out.printf("%20s: %-15s\n", "Director", dvd.getDirector());
            System.out.printf("%20s: %-15s\n", "Studio", dvd.getStudio());
            System.out.printf("%20s: %-15s\n", "User Note", dvd.getUserNote());
            System.out.printf("%20s: %-15s\n", "MPAA Rating", dvd.getMpaaRating());

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
