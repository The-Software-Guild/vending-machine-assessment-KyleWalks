package com.sg.dvdlibrary.controller;

import com.sg.dvdlibrary.dao.DvdLibraryDao;
import com.sg.dvdlibrary.dao.DvdLibraryDaoException;
import com.sg.dvdlibrary.dto.Dvd;
import com.sg.dvdlibrary.ui.DvdLibraryView;

import java.util.List;

public class DvdLibraryController {

    private final DvdLibraryView view;
    private final DvdLibraryDao dao;

    public DvdLibraryController(DvdLibraryDao dao, DvdLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    /**
     * Loops through the main menu of the application using
     * integer input.
     */
    public void run() {
        boolean keepGoing = true;
        int menuSelection;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        listDvds();
                        break;
                    case 2:
                        viewDvd();
                        break;
                    case 3:
                        createDvd();
                        break;
                    case 4:
                        removeDvd();
                        break;
                    case 5:
                        editDvd();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (DvdLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Creates a new DVD.
     *
     * @throws DvdLibraryDaoException
     */
    private void createDvd() throws DvdLibraryDaoException {
        view.displayCreateDvdBanner();
        Dvd newDvd = view.getNewDvdInfo();
        dao.addDvd(newDvd.getTitle(), newDvd);
        view.displayCreateSuccessBanner();
    }

    /**
     * Removes an entry from the database by using the
     * movie's title.
     *
     * @throws DvdLibraryDaoException if file saving/loading fails
     */
    private void removeDvd() throws DvdLibraryDaoException {
        view.displayRemoveDvdBanner();
        String dvdTitle = view.getDvdTitleChoice();
        Dvd removedDvd = dao.removeDvd(dvdTitle);
        view.displayRemoveResult(removedDvd);
    }

    /**
     * Lists all DVDs in the data base.
     *
     * @throws DvdLibraryDaoException if loading the database fails.
     */
    private void listDvds() throws DvdLibraryDaoException {
        view.displayDisplayAllBanner();
        List<Dvd> dvdList = dao.getAllDvds();
        view.displayDvdList(dvdList);
    }

    /**
     * Displays the DVD properties.
     *
     * @throws DvdLibraryDaoException if loading the database fails
     */
    private void viewDvd() throws DvdLibraryDaoException {
        view.displayDisplayDvdBanner();

        String dvdTitle = view.getDvdTitleChoice();
        Dvd dvd = dao.getDvd(dvdTitle);

        view.displayDvd(dvd);
    }

    /**
     * Edits a property of a DVD based on user integer input.
     *
     * @throws DvdLibraryDaoException if loading/saving the database fails
     */
    private void editDvd() throws DvdLibraryDaoException {
        view.displayDisplayDvdBanner();

        String dvdTitle = view.getDvdTitleChoice();
        Dvd dvd = dao.getDvd(dvdTitle);

        view.displayDvd(dvd);

        int editChoice = view.printEditMenuAndGetSelection();

        Dvd changedDvd = new Dvd(dvd);

        if (editChoice != 7)
            changedDvd = view.getNewDvdInfo(changedDvd, editChoice);
        else
            return;

        dao.removeDvd(dvd.getTitle());

        dao.addDvd(changedDvd.getTitle(), changedDvd);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    /**
     * Displays the main menu and retrieves user input.
     *
     * @return integer representing user menu choice.
     */
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

}