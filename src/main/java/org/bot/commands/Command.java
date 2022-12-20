package org.bot.commands;

import org.bot.MyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class Command {

    private String name;
    private String description = "";


    abstract void execute(Update update) throws TelegramApiException;

    public void sendText(long chatId, String response) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(response);
        message.setParseMode("Markdown");
        MyBot.getInstance().execute(message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
