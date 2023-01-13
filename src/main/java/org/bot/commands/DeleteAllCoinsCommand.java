package org.bot.commands;

import org.bot.cache.CacheFlyWeight;
import org.bot.commands.base.Command;
import org.bot.models.Coin;
import org.bot.observer.Notifier;
import org.bot.observer.UpdateRequest;
import org.bot.observer.actions.DeleteAllAction;
import org.bot.repositories.CoinRepository;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class DeleteAllCoinsCommand extends Notifier<Coin> implements Command {

    public DeleteAllCoinsCommand() {
        super();
        registerObserver(CacheFlyWeight.getInstance(Coin.class, CoinRepository.getInstance()));
        registerObserver(CoinRepository.getInstance());
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        notifyObservers(new DeleteAllAction<>(new UpdateRequest<>()));
        sendText(update.getMessage().getChatId(), "deleted all coins");
    }

    @Override
    public String getName() {
        return "/deleteall";
    }

    @Override
    public String getDescription() {
        return "to delete all coins from DB";
    }

    @Override
    public void accept(CommandVisitor visitor) {
        // no need to be visited
    }
}
