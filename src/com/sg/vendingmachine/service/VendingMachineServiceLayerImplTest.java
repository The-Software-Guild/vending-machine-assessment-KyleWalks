package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.sg.vendingmachine.dto.VendingItem;

class VendingMachineServiceLayerImplTest {

    final VendingMachineDaoFileImpl dao = new VendingMachineDaoFileImpl();

    @org.junit.jupiter.api.Test
    void getAllItems() throws Exception {
        dao.getAllItems();
        dao.getAllItems();
    }

    @org.junit.jupiter.api.Test
    void getVendingItem() throws Exception {
        VendingItem item = dao.getItem("Nonexistent Item");
        item = dao.getItem("Testitem");
    }

    @org.junit.jupiter.api.Test
    void dispenseVendingItem() throws Exception {
        dao.dispenseItem("NonexistentItem");
        dao.dispenseItem("TestItem");
    }
}