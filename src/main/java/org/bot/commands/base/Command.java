package org.bot.commands.base;

import org.bot.utils.TelegramSender;
import org.bot.visitor.CommandVisitable;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface Command extends CommandVisitable, TelegramSender {

    void execute(Update update) throws TelegramApiException;

    String getName();

    String getDescription();

    default boolean isUsable() {
        return true;
    }
}
