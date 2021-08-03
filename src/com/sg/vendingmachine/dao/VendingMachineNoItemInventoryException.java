package com.sg.vendingmachine.dao;

public class VendingMachineNoItemInventoryException extends Exception {
    public VendingMachineNoItemInventoryException(String message) {
        super(message);
    }

    public VendingMachineNoItemInventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
