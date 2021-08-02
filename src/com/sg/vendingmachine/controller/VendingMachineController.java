package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachineDaoException;
import com.sg.vendingmachine.dto.VendingItem;
import com.sg.vendingmachine.service.VendingMachineDataValidationException;
import com.sg.vendingmachine.service.VendingMachineDuplicateNameException;
import com.sg.vendingmachine.service.VendingMachinePersistenceException;
import com.sg.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.sg.vendingmachine.ui.VendingMachineView;

import java.util.List;

public class VendingMachineController {

    private final VendingMachineView view;
    private final VendingMachineServiceLayerImpl service;

    public VendingMachineController(VendingMachineServiceLayerImpl service, VendingMachineView view) {
        this.service = service;
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
            listItems();

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
        } catch (VendingMachineDaoException | VendingMachinePersistenceException | VendingMachineDataValidationException | VendingMachineDuplicateNameException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Creates a new VendingItem.
     *
     * @throws VendingMachineDaoException if loading/saving to the library fails.
     */
    private void createItem() throws VendingMachineDaoException, VendingMachinePersistenceException, VendingMachineDataValidationException, VendingMachineDuplicateNameException {
        view.displayCreateItemBanner();
        VendingItem newVendingItem = view.getNewItemInfo();
        service.createItem(newVendingItem);
        view.displayCreateSuccessBanner();
    }

    /**
     * Removes an entry from the database by using the
     * VendingItem name.
     *
     * @throws VendingMachineDaoException if file saving/loading fails
     */
    private void removeItem() throws VendingMachineDaoException, VendingMachinePersistenceException {
        view.displayRemoveItemBanner();
        String itemName = view.getItemNameChoice();
        VendingItem removedVendingItem = service.removeVendingItem(itemName);
        view.displayRemoveResult(removedVendingItem);
    }

    /**
     * Lists all VendingItems in the database.
     *
     * @throws VendingMachineDaoException if loading the database fails.
     */
    private void listItems() throws VendingMachineDaoException, VendingMachinePersistenceException {
        view.displayDisplayAllBanner();
        List<VendingItem> vendingItemList = service.getAllItems();
        view.displayItemList(vendingItemList);
    }

    /**
     * Displays the VendingItem properties.
     *
     * @throws VendingMachineDaoException if loading the database fails
     */
    private void viewItem() throws VendingMachineDaoException, VendingMachinePersistenceException {
        view.displayDisplayItemBanner();

        String itemName = view.getItemNameChoice();
        VendingItem vendingItem = service.getVendingItem(itemName);

        view.displayItem(vendingItem);
    }

    /**
     * Edits a property of a VendingItem based on user integer input.
     *
     * @throws VendingMachineDaoException if loading/saving the database fails
     */
    private void editItem() throws VendingMachineDaoException, VendingMachinePersistenceException, VendingMachineDataValidationException, VendingMachineDuplicateNameException {
        view.displayDisplayItemBanner();

        String itemName = view.getItemNameChoice();
        VendingItem vendingItem = service.getVendingItem(itemName);
        if (vendingItem == null) // TODO: Change to loop input
            return;

        view.displayItem(vendingItem);

        int editChoice = view.printEditMenuAndGetSelection();

        VendingItem changedVendingItem = new VendingItem(vendingItem);

        if (editChoice != 4)
            changedVendingItem = view.getNewItemInfo(changedVendingItem, editChoice);
        else
            return;

        service.removeVendingItem(vendingItem.getName());

        service.createItem(changedVendingItem);
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