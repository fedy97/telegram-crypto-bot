package org.bot.commands;

import org.bot.cache.CacheFlyWeight;
import org.bot.commands.base.Command;
import org.bot.models.Coin;
import org.bot.repositories.CoinRepository;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


public class PricesCommand implements Command {

    public PricesCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        List<Coin> coins = CacheFlyWeight.getInstance(Coin.class, CoinRepository.getInstance()).findAll();
        StringBuilder response = new StringBuilder();
        for (Coin coin : coins)
            response.append(coin.toShortString());
        if (response.length() == 0)
            response.append("Nothing to show");
        sendText(update.getMessage().getChatId(), response.toString());
    }

    @Override
    public String getName() {
        return "/prices";
    }

    @Override
    public String getDescription() {
        return "to show buy prices of all coins";
    }

    @Override
    public void accept(CommandVisitor visitor) {
        // no need to be visited
    }
}
