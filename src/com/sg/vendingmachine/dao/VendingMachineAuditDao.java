package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.service.VendingMachinePersistenceException;

public interface VendingMachineAuditDao {
    void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
