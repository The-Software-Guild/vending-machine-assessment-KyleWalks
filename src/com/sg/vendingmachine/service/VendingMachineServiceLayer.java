package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDaoException;
import com.sg.vendingmachine.dao.VendingMachineNoItemInventoryException;
import com.sg.vendingmachine.dto.VendingItem;

import java.util.List;

public interface VendingMachineServiceLayer {

    List<VendingItem> getAllItems() throws
            VendingMachinePersistenceException, VendingMachineDaoException;

    VendingItem getVendingItem(String itemName) throws
            VendingMachinePersistenceException, VendingMachineDaoException, VendingMachineNoItemInventoryException;

    VendingItem dispenseVendingItem(String itemName) throws
            VendingMachinePersistenceException, VendingMachineDaoException, VendingMachineNoItemInventoryException;
}
