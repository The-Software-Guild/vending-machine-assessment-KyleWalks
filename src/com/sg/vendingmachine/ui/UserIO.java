package com.sg.vendingmachine.ui;

import java.math.BigDecimal;

public interface UserIO {
    void print(String msg);

    String readString(String prompt);

    BigDecimal readBigDecimal(String prompt);

    BigDecimal readBigDecimal(String prompt, BigDecimal min);
}
