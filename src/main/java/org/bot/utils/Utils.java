package org.bot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.bot.MyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    public static void sendNotFoundMessage(Update update) throws TelegramApiException {
        log.warn("Command not found");
        SendMessage sendMessage = new SendMessage();
        long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Sorry, I didn't understand that command. Type /help for a list of available commands.");
        MyBot.getInstance().execute(sendMessage);
    }

}
