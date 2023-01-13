package org.bot.utils;

import com.vdurmont.emoji.EmojiParser;

public class EmojiFactory {

    private EmojiFactory() {}
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
