package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dto.VendingItem;

import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineDao dao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao) {
        this.dao = dao;
    }

    @Override
    public void createItem(VendingItem item) throws
            VendingMachineDuplicateNameException,
            VendingMachineDataValidationException,
            VendingMachinePersistenceException {

        if (dao.getItem(item.getStudentId()) != null) {
            throw new VendingMachineDuplicateNameException(
                    "ERROR: Could not create student.  Student Id "
                            + item.getStudentId()
                            + " already exists");
        }

        // Now validate all the fields on the given Student object.
        // This method will throw an
        // exception if any of the validation rules are violated.
        validateItemData(item);

        // We passed all our business rules checks so go ahead
        // and persist the Student object
        dao.addItem(item.getStudentId(), item);

    }

    @Override
    public List<VendingItem> getAllItems() throws VendingMachinePersistenceException {
        return null;
    }

    @Override
    public VendingItem getVendingItem(String itemName) throws VendingMachinePersistenceException {
        return null;
    }

    @Override
    public VendingItem removeVendingItem(String itemName) throws VendingMachinePersistenceException {
        return null;
    }

    private void validateItemData(VendingItem item) throws VendingMachineDataValidationException {
        if (item.getFirstName() == null
                || item.getFirstName().trim().length() == 0
                || item.getLastName() == null
                || item.getLastName().trim().length() == 0
                || item.getCohort() == null
                || item.getCohort().trim().length() == 0) {

            throw new VendingMachineDataValidationException(
                    "ERROR: All fields [First Name, Last Name, Cohort] are required.");
        }
    }
}
