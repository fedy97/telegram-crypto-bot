package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.cache.CoinCache;
import org.bot.commands.base.Command;
import org.bot.models.Coin;
import org.bot.observer.Notifier;
import org.bot.observer.UpdateRequest;
import org.bot.observer.actions.DeleteAction;
import org.bot.repositories.CoinRepository;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class DeleteCoinCommand extends Notifier<Coin> implements Command {

    public DeleteCoinCommand() {
        super();
        registerObserver(CoinRepository.getInstance());
        registerObserver(CoinCache.getInstance());
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 2) {
            log.error("Invalid command format");
            sendText(update.getMessage().getChatId(), "Invalid command format. Use /delete <ticker>");
            return;
        }
        String ticker = parts[1].toUpperCase();
        UpdateRequest<Coin> updateRequest = new UpdateRequest<>();
        updateRequest.setCol("ticker");
        updateRequest.setVal(ticker);
        notifyObservers(new DeleteAction<>(updateRequest));
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
