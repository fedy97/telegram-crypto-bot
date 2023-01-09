package org.bot.commands.base;

import org.bot.MyBot;
import org.bot.visitor.CommandVisitable;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Command extends CommandVisitable {

    void execute(Update update) throws TelegramApiException;

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
    String getName();

    String getDescription();
}
