package org.bot.commands;

import org.bot.commands.base.Command;
import org.bot.repositories.MongoCoinRepository;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class DeleteAllCoinsCommand implements Command {

    public DeleteAllCoinsCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        MongoCoinRepository.getInstance().deleteAll();
        sendText(update.getMessage().getChatId(), "deleted all coins");
    }

    @Override
    public String getName() {
        return "/deleteall";
    }

    @Override
    public String getDescription() {
        return "delete all coins from DB";
    }

}
