package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoException;
import com.sg.vendingmachine.dao.VendingMachineNoItemInventoryException;
import com.sg.vendingmachine.dto.VendingItem;

import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private final VendingMachineDao dao;
    private final VendingMachineAuditDao auditDao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    /**
     * Retrieves all the vending items in the database.
     *
     * @return the hashmap of all vending items.
     * @throws VendingMachineDaoException if the dao fails to save/load the database.
     */
    @Override
    public List<VendingItem> getAllItems() throws
            VendingMachineDaoException {

        return dao.getAllItems();
    }

    /**
     * Retrieves the vending item specified by name.
     *
     * @param itemName the name of the vending item
     * @return the vending item
     * @throws VendingMachineDaoException if the dao fails to load/save the database.
     * @throws VendingMachineNoItemInventoryException if the item has a count of zero.
     */
    @Override
    public VendingItem getVendingItem(String itemName) throws
            VendingMachineDaoException, VendingMachineNoItemInventoryException {

        VendingItem item = dao.getItem(itemName);

        if (item != null)
            if (item.getCount() == 0)
                throw new VendingMachineNoItemInventoryException("Item is out of stock.\n");

        return item;
    }

    /**
     * Dispenses a VendingItem from the machine.
     *
     * @param itemName the name of the item being dispensed
     * @throws VendingMachinePersistenceException if the audit fails to log to the audit file.
     * @throws VendingMachineDaoException if the dao fails to load/save the database.
     */
    @Override
    public void dispenseVendingItem(String itemName) throws
            VendingMachinePersistenceException,
            VendingMachineDaoException, VendingMachineNoItemInventoryException {

        auditDao.writeAuditEntry("VendingItem " + itemName + " DISPENSED.");
        dao.dispenseItem(itemName);
    }

}
