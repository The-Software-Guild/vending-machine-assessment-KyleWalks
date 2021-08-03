package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoException;
import com.sg.vendingmachine.dao.VendingMachineNoItemInventoryException;
import com.sg.vendingmachine.dto.VendingItem;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public void createItem(VendingItem item) throws
            VendingMachineDuplicateNameException,
            VendingMachineDataValidationException,
            VendingMachinePersistenceException,
            VendingMachineDaoException {

        if (dao.getItem(item.getName()) != null) {
            throw new VendingMachineDuplicateNameException(
                    "ERROR: Could not create vending item.  Item "
                            + item.getName()
                            + " already exists");
        }

        validateItemData(item);

        dao.addItem(item.getName(), item);

        auditDao.writeAuditEntry("Vending Item " + item.getName() + " CREATED.");
    }

    @Override
    public List<VendingItem> getAllItems() throws
            VendingMachinePersistenceException,
            VendingMachineDaoException {

        return dao.getAllItems();
    }

    @Override
    public VendingItem getVendingItem(String itemName) throws
            VendingMachinePersistenceException,
            VendingMachineDaoException {

        return dao.getItem(itemName);
    }

    @Override
    public VendingItem dispenseVendingItem(String itemName) throws
            VendingMachinePersistenceException,
            VendingMachineDaoException, VendingMachineNoItemInventoryException {

        if (dao.getItem(itemName).getCount() == 0)
            throw new VendingMachineNoItemInventoryException("Item is out of stock.");

        auditDao.writeAuditEntry("VendingItem " + itemName + " DISPENSED.");
        return dao.dispenseItem(itemName);
    }

    private void validateItemData(VendingItem item) throws VendingMachineDataValidationException {
        if (item.getName() == null
                || item.getName().trim().length() == 0
                || item.getPrice() == null
                || item.getPrice().compareTo(new BigDecimal(0)) < 0
                || item.getCount() < 0) {

            throw new VendingMachineDataValidationException(
                    "ERROR: All fields [Name, Price, Count] are required.");
        }
    }
}
