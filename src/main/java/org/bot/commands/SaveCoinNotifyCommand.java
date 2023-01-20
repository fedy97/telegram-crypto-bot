package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.cache.CacheFlyWeight;
import org.bot.commands.base.Command;
import org.bot.models.CoinNotify;
import org.bot.observer.Notifier;
import org.bot.observer.UpdateRequest;
import org.bot.observer.actions.AddAction;
import org.bot.repositories.CoinNotifyRepository;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class SaveCoinNotifyCommand extends Notifier<CoinNotify> implements Command {

    public SaveCoinNotifyCommand() {
        super();
        registerObserver(CacheFlyWeight.getInstance(CoinNotify.class, CoinNotifyRepository.getInstance()));
        registerObserver(CoinNotifyRepository.getInstance());
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        String ticker = parts[1].toUpperCase();
        Integer percentage = Integer.parseInt(parts[2]);
        CoinNotify coin = new CoinNotify();
        coin.setTicker(ticker);
        coin.setPercentageChange(percentage);
        coin.setChatId(update.getMessage().getChatId());
        UpdateRequest<CoinNotify> updateRequest = new UpdateRequest<>();
        updateRequest.setEntity(coin);
        notifyObservers(new AddAction<>(updateRequest));
        sendText(update.getMessage().getChatId(), "I will send alert when " + ticker + " is " + percentage + "% up");
    }

    @Override
    public String getName() {
        return "/notify";
    }

    @Override
    public String getDescription() {
        return "<coin> <% change> to save a new notifier for a coin in this chat";
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitSaveCoinNotifyCommand();
    }
}
