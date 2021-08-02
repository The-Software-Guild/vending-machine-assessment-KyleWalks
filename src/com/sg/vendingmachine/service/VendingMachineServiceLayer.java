package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDaoException;
import com.sg.vendingmachine.dto.VendingItem;

import java.util.List;

public interface VendingMachineServiceLayer {
    void createItem(VendingItem item) throws
            VendingMachineDuplicateNameException,
            VendingMachineDataValidationException,
            VendingMachinePersistenceException, VendingMachineDaoException;

    List<VendingItem> getAllItems() throws
            VendingMachinePersistenceException, VendingMachineDaoException;

    VendingItem getVendingItem(String itemName) throws
            VendingMachinePersistenceException, VendingMachineDaoException;

    VendingItem removeVendingItem(String itemName) throws
            VendingMachinePersistenceException, VendingMachineDaoException;
}
