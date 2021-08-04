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

    /**
     * Constructor for the VendingMachineController
     *
     * @param service Service layer of the application
     * @param view View layer of the application
     */
    public VendingMachineController(VendingMachineServiceLayerImpl service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    /**
     * Loops through the main menu of the application
     */
    public void run() {
        VendingItem currItem = null;
        BigDecimal enteredMoney;
        boolean dispensed = false;
        try {
            while (true) {
                listItems();
                // Query for money
                enteredMoney = enterMoney();
                if (enteredMoney.equals(new BigDecimal("0")))
                    return;
                while (currItem == null) {
                    try {
                        currItem = getChoice();
                    } catch (VendingMachineNoItemInventoryException e) {
                        view.displayErrorMessage(e.getMessage());
                    }
                }

                // Query item choice
                while (!dispensed) {
                    try {
                        dispensed = dispenseItem(currItem, enteredMoney);
                    } catch (VendingMachineInsufficientFundsException e) {
                        view.displayErrorMessage(e.getMessage());
                    }
                }
            }
        } catch (VendingMachineDaoException | VendingMachinePersistenceException | VendingMachineNoItemInventoryException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Takes a sum of money as input from the user.
     *
     * @return The sum of money entered by the user.
     */
    private BigDecimal enterMoney() {
        view.displayEnterMoneyBanner();
        return view.getCurrency();
    }

    /**
     * Querys and returns an item choice from the user.
     *
     * @return The VendingItem selected by the user.
     * @throws VendingMachineDaoException If the application fails to load the database file.
     * @throws VendingMachinePersistenceException If the audit log fails to load/save to the audit file.
     * @throws VendingMachineNoItemInventoryException If the item selected is out of stock.
     */
    private VendingItem getChoice() throws VendingMachineDaoException, VendingMachinePersistenceException, VendingMachineNoItemInventoryException {
        String itemName = view.getItemNameChoice();

        // Check for exit num
        if (itemName.equals("0"))
            return null;

        VendingItem selItem = service.getVendingItem(itemName);

        view.displayItem(selItem);

        return selItem;
    }

    /**
     * Dispenses an item to the user.
     *
     * @param item The vending item to be dispensed.
     * @param enteredMoney The sum of money entered by the user.
     * @return true if the item was successfully dispensed.
     * @throws VendingMachineInsufficientFundsException If the user has entered a sum of money less than the
     *                                                  item's price.
     * @throws VendingMachineDaoException If the DAO fails to load/save to the database.
     * @throws VendingMachinePersistenceException If the audit fails to load/save to the audit file.
     */
    private boolean dispenseItem(VendingItem item, BigDecimal enteredMoney) throws VendingMachineInsufficientFundsException, VendingMachineDaoException, VendingMachinePersistenceException, VendingMachineNoItemInventoryException {
        Change change = new Change(item, enteredMoney);

        if (change.getChange() == null)
            throw new VendingMachineInsufficientFundsException("Insufficient funds entered.\nCredit: $"
                                                                                        + enteredMoney + "\n");

        int[] changeAsCoins = change.getChangeAsCoins();
        service.dispenseVendingItem(item.getName());

        view.displayChangeResult(change, changeAsCoins);

        return true;
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
}