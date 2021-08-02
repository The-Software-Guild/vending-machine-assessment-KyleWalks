package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingItem;

import java.util.List;

public interface VendingMachineDao {
    /**
     * Adds the given DVD to the library and associates it with the given
     * DVD title. If there is already a DVD associated with the given
     * DVD title it will return that DVD object, otherwise it will
     * return null.
     *
     * @param itemName title with which DVD is to be associated
     * @param vendingItem DVD to be added to the library
     * @return the DVD object previously associated with the given
     * DVD title if it exists, null otherwise
     * @throws VendingMachineDaoException
     */
    VendingItem addItem(String itemName, VendingItem vendingItem)
            throws VendingMachineDaoException;

    /**
     * Returns a List of all DVDs on the library.
     *
     * @return DVD List containing all DVDs on the library.
     * @throws VendingMachineDaoException
     */
    List<VendingItem> getAllItems()
            throws VendingMachineDaoException;

    /**
     * Returns the DVD object associated with the given DVD title.
     * Returns null if no such DVD exists
     *
     * @param itemName Title of the DVD to retrieve
     * @return the DVD object associated with the given DVD title,
     * null if no such DVD exists
     * @throws VendingMachineDaoException
     */
    VendingItem getItem(String itemName)
            throws VendingMachineDaoException;

    /**
     * Removes from the library the DVD associated with the given title.
     * Returns the DVD object that is being removed or null if
     * there is no DVD associated with the given title
     *
     * @param itemName title of DVD to be removed
     * @return DVD object that was removed or null if no DVD
     * was associated with the given DVD title
     * @throws VendingMachineDaoException
     */
    VendingItem removeItem(String itemName)
            throws VendingMachineDaoException;
}