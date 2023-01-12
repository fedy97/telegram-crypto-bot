package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.cache.CoinCacheProxy;
import org.bot.commands.base.Command;
import org.bot.models.Coin;
import org.bot.observer.Notifier;
import org.bot.observer.UpdateRequest;
import org.bot.observer.actions.AddAction;
import org.bot.repositories.CoinRepository;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class SaveCoinCommand extends Notifier<Coin> implements Command {

    public SaveCoinCommand() {
        super();
        registerObserver(CoinCacheProxy.getInstance());
        registerObserver(CoinRepository.getInstance());
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        String ticker = parts[1].toUpperCase();
        double price = Double.parseDouble(parts[2].replace(",", "."));
        Coin coin = new Coin();
        coin.setTicker(ticker);
        coin.setPrice(price);
        UpdateRequest<Coin> updateRequest = new UpdateRequest<>();
        updateRequest.setEntity(coin);
        notifyObservers(new AddAction<>(updateRequest));
        sendText(update.getMessage().getChatId(), "Coin saved");
    }

    @Override
    public String getName() {
        return "/save";
    }

    @Override
    public String getDescription() {
        return "<coin> to save a new coin with its buy price";
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitSaveCoinCommand();
    }
}
