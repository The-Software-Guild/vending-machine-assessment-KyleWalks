package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VendingItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class VendingMachineDaoFileImplTest {

    final VendingMachineDaoFileImpl dao = new VendingMachineDaoFileImpl();

    @Test
    void getAllItems() throws Exception {
        dao.getAllItems();
    }

    @Test
    void getItem() throws Exception {
        VendingItem item = dao.getItem("Nonexistent Item");
        item = dao.getItem("TestItem");
    }

    @Test
    void dispenseItem() throws Exception {
        dao.dispenseItem("Nonexistent Item");
        dao.dispenseItem("TestItem");
    }

    @Test
    void print() throws Exception {
        String msg = "0exHello.//";
        System.out.println(msg);
    }

    @Test
    void readString() throws Exception {
        String test = "0exHello.//";
    }
    
    @Test
    void readBigDecimal() throws Exception {
        String test ="5.00";
        BigDecimal val = new BigDecimal("-1");
        while (val.equals(new BigDecimal("-1"))) {
            try {
                val = new BigDecimal(test);
            } catch (NumberFormatException e) {
                System.out.println("Input error. Please try again.");
            }
        }
    }

    @Test
    void testReadBigDecimal() throws Exception {
        BigDecimal result;
        BigDecimal min = new BigDecimal("0");
        String test ="5.00";

        do {
            result = new BigDecimal(test);
        } while (result.compareTo(min) < 0);

        System.out.println(result);
    }
}