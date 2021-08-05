package com.sg.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Change {

    /**
     * Base values for the denominations of coins as whole numbers.
     */
    private enum Coins {
        PENNY(new BigDecimal("1")),
        NICKEL(new BigDecimal("5")),
        DIME(new BigDecimal("10")),
        QUARTER(new BigDecimal("25"));

        private final BigDecimal val;

        Coins(BigDecimal val) {
            this.val = val;
        }

        public BigDecimal getVal() {
            return val;
        }
    }

    private final VendingItem item;
    private final BigDecimal amountEntered;
    private BigDecimal change;

    public Change(VendingItem item, BigDecimal amount) {
        this.item = item;
        this.amountEntered = amount;
        this.change = calcChange();
    }

    /**
     * Calculates the change owed to the user.
     *
     * @return the sum owed to the user in dollar format or null if the sum entered is less than item price.
     */
    private BigDecimal calcChange() {
        BigDecimal price = item.getPrice();
        if (price.compareTo(amountEntered) > 0)
            return null;

        this.change = amountEntered.subtract(price);
        this.change = change.round(new MathContext(5));

        return this.change;
    }

    public BigDecimal getChange() {
        return change;
    }

    /**
     * Calculates the change owed to the user in coins.
     *
     * @return an array containing the number of coins of each denomination owed. [Quarter, Dime, Nickel, Penny]
     */
    public int[] getChangeAsCoins() {
        // Quarter, dime, nickel, penny
        int[] coins = {0, 0, 0, 0};
        BigDecimal changeCount = this.change;

        // Check if exact amount was given.
        if (changeCount.compareTo(new BigDecimal("0")) == 0)
            return new int[]{0, 0, 0, 0};

        BigDecimal largeDenom, smallDenom;

        // Split the sum of money at the decimal point
        String[] changeStr;
        changeStr = changeCount.toString().split("\\.");

        largeDenom = new BigDecimal(changeStr[0]);
        smallDenom = new BigDecimal(changeStr[1]);

        // Check if amount entered is exact dollar amount.
        if (smallDenom.compareTo(new BigDecimal("0")) == 0) {
            BigDecimal quarterAsDecimal = Coins.QUARTER.getVal().divide(new BigDecimal("100"), RoundingMode.DOWN);

            // Dollar amount / 0.25 = # Quarters
            coins[0] = (changeCount.divide(quarterAsDecimal, RoundingMode.DOWN)).intValueExact();

            return coins;
        } else {
            // Calculate the number of coins owed in each denomination of coin from largest to smallest.
            if (smallDenom.compareTo(Coins.QUARTER.getVal()) >= 0) {
                // Get the number of quarters by (fraction dollar amount / 25)
                BigDecimal numQuarters = (smallDenom.divide(Coins.QUARTER.getVal(), RoundingMode.DOWN));

                coins[0] = numQuarters.intValueExact();

                numQuarters = Coins.QUARTER.getVal().multiply(numQuarters);

                // Subtract the amount of quarters owed.
                smallDenom = smallDenom.subtract(numQuarters);
            }
            if (smallDenom.divide(Coins.DIME.getVal(), RoundingMode.DOWN).compareTo(Coins.PENNY.getVal()) >= 0) {
                // Get the number of dimes by (fraction dollar amount / 10)
                BigDecimal numDimes = (smallDenom.divide(Coins.DIME.getVal(), RoundingMode.DOWN));

                coins[1] = numDimes.intValueExact();

                numDimes = Coins.DIME.getVal().multiply(numDimes);

                // Subtract the amount of dimes owed.
                smallDenom = smallDenom.subtract(numDimes);
            }
            if (smallDenom.divide(Coins.NICKEL.getVal(), RoundingMode.DOWN).compareTo(Coins.PENNY.getVal()) >= 0) {
                // Get the number of nickels by (fraction dollar amount / 5)
                BigDecimal numNickels = (smallDenom.divide(Coins.NICKEL.getVal(), RoundingMode.DOWN));

                coins[2] = numNickels.intValueExact();

                numNickels = Coins.NICKEL.getVal().multiply(numNickels);

                // Subtract the amount of dimes owed.
                smallDenom = smallDenom.subtract(numNickels);
            }
            if (smallDenom.divide(Coins.PENNY.getVal(), RoundingMode.DOWN).compareTo(Coins.PENNY.getVal()) >= 0) {
                // Get the number of pennies by (fraction dollar amount / 1)
                BigDecimal numPennys = (smallDenom.divide(Coins.PENNY.getVal(), RoundingMode.DOWN));

                coins[3] = numPennys.intValueExact();

                numPennys = Coins.PENNY.getVal().multiply(numPennys);
            }
            if (largeDenom.compareTo(new BigDecimal("1")) >= 0) {
                // Calculate number of quarters owed for whole dollar amount (whole dollar / 0.25)
                BigDecimal quarterAsDecimal = Coins.QUARTER.getVal().divide(new BigDecimal("100"), 4, RoundingMode.DOWN);
                coins[0] += (largeDenom.divide(quarterAsDecimal, RoundingMode.DOWN)).intValueExact();
            }
        }

        return coins;
    }
}
