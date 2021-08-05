package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingItem;

import java.util.List;

public interface VendingMachineDao {

    /**
     * Returns a List of all VendingItems in the vending machine.
     *
     * @return VendingItem List containing all VendingItems in the vending machine.
     * @throws VendingMachineDaoException if the dao fails to load/save the database. if the dao fails to load/save the database.
     */
    List<VendingItem> getAllItems()
            throws VendingMachineDaoException;

    /**
     * Returns the VendingItem object associated with the given VendingItem name.
     * Returns null if no such VendingItem exists
     *
     * @param itemName Name of the VendingItem to retrieve
     * @return the VendingItem object associated with the given VendingItem name,
     * null if no such VendingItem exists
     * @throws VendingMachineDaoException if the dao fails to load/save the database.
     */
    VendingItem getItem(String itemName)
            throws VendingMachineDaoException;

    /**
     * Removes from the vending machine the VendingItem associated with the given name.
     * Returns the VendingItem object that is being removed or null if
     * there is no VendingItem associated with the given name
     *
     * @param itemName name of VendingItem to be removed
     * @return VendingItem object that was removed or null if no VendingItem
     * was associated with the given VendingItem name
     * @throws VendingMachineDaoException if the dao fails to load/save the database.
     */
    VendingItem dispenseItem(String itemName)
            throws VendingMachineDaoException;
}