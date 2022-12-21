package org.bot.commands;

import org.bot.commands.base.Command;
import org.bot.repositories.MongoCoinRepository;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class DeleteCoinCommand implements Command {

    public DeleteCoinCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 2) {
            sendText(update.getMessage().getChatId(), "Invalid command format. Use /delete <ticker>");
            return;
        }
        String ticker = parts[1];
        MongoCoinRepository.getInstance().deleteByValue("ticker", ticker);
        sendText(update.getMessage().getChatId(), ticker +  " deleted");
    }

    @Override
    public String getName() {
        return "/delete";
    }

    @Override
    public String getDescription() {
        return "delete a coin from DB";
    }

}
