package org.bot.commands;

import org.bot.commands.base.Command;
import org.bot.models.Coin;
import org.bot.utils.fetchers.CoinFetcherFactory;
import org.bot.utils.fetchers.base.DataFetcher;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


public class PricesCommand implements Command {

    public PricesCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        DataFetcher<Coin> dataFetcher = CoinFetcherFactory.getInstance().createDataFetcher();
        List<Coin> coins = dataFetcher.fetchAll();
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
        return "show buy prices of all coins";
    }

}
