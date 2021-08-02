package com.sg.vendingmachine.dto;

import java.math.BigDecimal;

public class Change {

    private enum Coins {
        PENNY, NICKEL, DIME, QUARTER
    }

    private VendingItem item;
    private BigDecimal amountEntered;
    private BigDecimal change;

    public Change(VendingItem item, BigDecimal amount) {
        this.item = item;
        this.amountEntered = amount;
        this.change = new BigDecimal(0);
    }

    public VendingItem getItem() {
        return item;
    }

    public void setItem(VendingItem item) {
        this.item = item;
    }

    public BigDecimal getAmountEntered() {
        return amountEntered;
    }

    public void setAmountEntered(BigDecimal amountEntered) {
        this.amountEntered = amountEntered;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }
}
