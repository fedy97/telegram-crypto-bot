package org.bot.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberOperations {
    private NumberOperations() {}
    public static BigDecimal roundFloat(double num, int decimals) {
        // Set the decimal number to round
        BigDecimal number = BigDecimal.valueOf(num);
        // Round the number
        return number.setScale(decimals, RoundingMode.HALF_EVEN);
    }
}
