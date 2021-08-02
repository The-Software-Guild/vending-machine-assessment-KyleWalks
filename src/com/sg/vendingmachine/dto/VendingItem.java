package com.sg.vendingmachine.dto;

import java.math.BigDecimal;

public class VendingItem {
    private String name;
    private BigDecimal price;
    private int count;

    public VendingItem(String itemName) {
        this.name = itemName;
    }

    public VendingItem(String itemName, BigDecimal itemCost, int invCount) {
        this.name = itemName;
        this.price = itemCost;
        this.count = invCount;
    }

    public VendingItem(VendingItem item) {
        this.name = item.name;
        this.price = item.price;
        this.count = item.count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
