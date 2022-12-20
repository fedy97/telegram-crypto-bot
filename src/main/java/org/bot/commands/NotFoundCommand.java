package org.bot.commands;

import org.bot.MyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class NotFoundCommand extends Command {

    private static NotFoundCommand instance;

    private NotFoundCommand() {
        // Private constructor to prevent instantiation
        setName("notFound");
    }

    public static NotFoundCommand getInstance() {
        if (instance == null) {
            instance = new NotFoundCommand();
        }
        return instance;
    }
    @Override
    public void execute(Update update) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Sorry, I didn't understand that command. Type /help for a list of available commands.");
        MyBot.getInstance().execute(sendMessage);
    }

}
