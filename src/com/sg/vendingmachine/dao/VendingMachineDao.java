package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingItem;

import java.util.List;

public interface VendingMachineDao {
    /**
     * Adds the given VendingItem to the vending machine and associates it with the given
     * item name. If there is already a VendingItem associated with the given
     * item name, it will return that VendingItem object, otherwise it will
     * return null.
     *
     * @param itemName name with which VendingItem is to be associated
     * @param vendingItem VendingItem to be added to the vending machine
     * @return the VendingItem object previously associated with the given
     * item name if it exists, null otherwise
     * @throws VendingMachineDaoException
     */
    VendingItem addItem(String itemName, VendingItem vendingItem)
            throws VendingMachineDaoException;

    /**
     * Returns a List of all VendingItems in the vending machine.
     *
     * @return VendingItem List containing all VendingItems in the vending machine.
     * @throws VendingMachineDaoException
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
     * @throws VendingMachineDaoException
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
     * @throws VendingMachineDaoException
     */
    VendingItem removeItem(String itemName)
            throws VendingMachineDaoException;
}