package org.bot.utils;

import org.bot.MyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramSender {
    default void sendText(long chatId, String response) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(response);
        message.setParseMode("Markdown");
        message.disableWebPagePreview();
        try {
            MyBot.getInstance().execute(message);
        } catch (Exception e) {
            // do nothing
        }
    }
}
