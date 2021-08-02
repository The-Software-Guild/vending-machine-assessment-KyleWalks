package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoException;
import com.sg.vendingmachine.dto.VendingItem;
import com.sg.vendingmachine.ui.VendingMachineView;

import java.util.List;

public class VendingMachineController {

    private final VendingMachineView view;
    private final VendingMachineDao dao;

    public VendingMachineController(VendingMachineDao dao, VendingMachineView view) {
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
                        listItems();
                        break;
                    case 2:
                        viewItem();
                        break;
                    case 3:
                        createItem();
                        break;
                    case 4:
                        removeItem();
                        break;
                    case 5:
                        editItem();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (VendingMachineDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Creates a new DVD.
     *
     * @throws VendingMachineDaoException if loading/saving to the library fails.
     */
    private void createItem() throws VendingMachineDaoException {
        view.displayCreateItemBanner();
        VendingItem newVendingItem = view.getNewItemInfo();
        dao.addItem(newVendingItem.getItemName(), newVendingItem);
        view.displayCreateSuccessBanner();
    }

    /**
     * Removes an entry from the database by using the
     * movie's title.
     *
     * @throws VendingMachineDaoException if file saving/loading fails
     */
    private void removeItem() throws VendingMachineDaoException {
        view.displayRemoveItemBanner();
        String itemName = view.getItemNameChoice();
        VendingItem removedVendingItem = dao.removeItem(itemName);
        view.displayRemoveResult(removedVendingItem);
    }

    /**
     * Lists all DVDs in the data base.
     *
     * @throws VendingMachineDaoException if loading the database fails.
     */
    private void listItems() throws VendingMachineDaoException {
        view.displayDisplayAllBanner();
        List<VendingItem> vendingItemList = dao.getAllItems();
        view.displayItemList(vendingItemList);
    }

    /**
     * Displays the DVD properties.
     *
     * @throws VendingMachineDaoException if loading the database fails
     */
    private void viewItem() throws VendingMachineDaoException {
        view.displayDisplayItemBanner();

        String itemName = view.getItemNameChoice();
        VendingItem vendingItem = dao.getItem(itemName);

        view.displayItem(vendingItem);
    }

    /**
     * Edits a property of a DVD based on user integer input.
     *
     * @throws VendingMachineDaoException if loading/saving the database fails
     */
    private void editItem() throws VendingMachineDaoException {
        view.displayDisplayItemBanner();

        String itemName = view.getItemNameChoice();
        VendingItem vendingItem = dao.getItem(itemName);

        view.displayItem(vendingItem);

        int editChoice = view.printEditMenuAndGetSelection();

        VendingItem changedVendingItem = new VendingItem(vendingItem);

        if (editChoice != 7)
            changedVendingItem = view.getNewItemInfo(changedVendingItem, editChoice);
        else
            return;

        dao.removeItem(vendingItem.getItemName());

        dao.addItem(changedVendingItem.getItemName(), changedVendingItem);
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