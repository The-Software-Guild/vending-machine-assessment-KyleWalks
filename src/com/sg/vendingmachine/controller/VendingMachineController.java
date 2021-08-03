package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachineDaoException;
import com.sg.vendingmachine.dao.VendingMachineNoItemInventoryException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.VendingItem;
import com.sg.vendingmachine.service.*;
import com.sg.vendingmachine.ui.VendingMachineView;

import java.math.BigDecimal;
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
        VendingItem currItem;
        BigDecimal enteredMoney;
        try {
            while (keepGoing) {
                listItems();
                // Query for money
                enteredMoney = enterMoney();
                // Query item choice
                currItem = getChoice();
                dispenseItem(currItem, enteredMoney);
                keepGoing = false;
            }
            exitMessage();
        } catch (VendingMachineDaoException | VendingMachinePersistenceException | VendingMachineInsufficientFundsException | VendingMachineNoItemInventoryException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private BigDecimal enterMoney() {
        view.displayEnterMoneyBanner();
        return view.getCurrency();
    }

    private VendingItem getChoice() throws VendingMachineDaoException, VendingMachinePersistenceException {
        String itemName = view.getItemNameChoice();
        VendingItem selItem = service.getVendingItem(itemName);

        view.displayItem(selItem);

        return selItem;
    }

    private BigDecimal dispenseItem(VendingItem item, BigDecimal enteredMoney) throws VendingMachineInsufficientFundsException, VendingMachineDaoException, VendingMachinePersistenceException, VendingMachineNoItemInventoryException {
        BigDecimal change = new Change(item, enteredMoney).getChange();

        if (change == null)
            throw new VendingMachineInsufficientFundsException("Insufficient funds entered.");

        service.dispenseVendingItem(item.getName());

        view.displayChangeResult(change);

        return change;
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