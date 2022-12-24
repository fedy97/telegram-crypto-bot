package org.bot.commands;

import org.bot.commands.base.Command;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class StartCommand implements Command {

    public StartCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        long chatId = update.getMessage().getChatId();
        String response = "Welcome to the bot! Type /help to see a list of available commands.";
        sendText(chatId, response);
    }

    @Override
    public String getName() {
        return "/start";
    }

    @Override
    public String getDescription() {
        return "to show welcome message";
    }

    @Override
    public void accept(CommandVisitor visitor) {
        // no need to be visited
    }
}
