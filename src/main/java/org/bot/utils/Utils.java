package org.bot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class Utils {


    private Utils() {

    }


    public static String getEnvVar(String name) {
        return System.getenv(name);
    }

    public static BigDecimal roundFloat(double num, int decimals) {
        // Set the decimal number to round
        BigDecimal number = BigDecimal.valueOf(num);
        // Round the number
        return number.setScale(decimals, RoundingMode.HALF_EVEN);
    }

    public static void wrapStringWith(StringBuilder sb, String toWrap, String wrapper) {
        sb.append(wrapper);
        sb.append(toWrap);
        sb.append(wrapper);
    }

    public static String findEmoji(double change) {
        if (change >= 50)
            return EmojiParser.parseToUnicode(":rocket:");
        if (change >= 10)
            return EmojiParser.parseToUnicode(":tada:");
        if (change <= -50)
            return EmojiParser.parseToUnicode(":skull_and_crossbones:");
        if (change <= -10)
            return EmojiParser.parseToUnicode(":x:");
        return "";
    }

}
