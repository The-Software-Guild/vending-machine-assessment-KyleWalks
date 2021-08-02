package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dto.VendingItem;

import java.util.List;

public interface VendingMachineServiceLayer {
    void createItem(VendingItem item) throws
            VendingMachineDuplicateNameException,
            VendingMachineDataValidationException,
            VendingMachinePersistenceException;

    List<VendingItem> getAllItems() throws
            VendingMachinePersistenceException;

    VendingItem getVendingItem(String itemName) throws
            VendingMachinePersistenceException;

    VendingItem removeVendingItem(String itemName) throws
            VendingMachinePersistenceException;
}
