package org.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class StartCommand extends Command {

    public StartCommand() {
        super();
        setName("/start");
        setDescription("show welcome message");
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        long chatId = update.getMessage().getChatId();
        String response = "Welcome to the bot! Type /help to see a list of available commands.";
        sendText(chatId, response);
    }

}
