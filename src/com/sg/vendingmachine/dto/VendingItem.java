package com.sg.vendingmachine.dto;

import java.math.BigDecimal;

public class VendingItem {
    private String itemName;
    private BigDecimal itemCost;
    private int invCount;

    public VendingItem(String itemName, BigDecimal itemCost, int invCount) {
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.invCount = invCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    public int getInvCount() {
        return invCount;
    }

    public void setInvCount(int invCount) {
        this.invCount = invCount;
    }
}
