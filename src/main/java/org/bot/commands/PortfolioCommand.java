package org.bot.commands;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bot.commands.base.Command;
import org.bot.models.Portfolio;
import org.bot.models.PortfolioLink;
import org.bot.providers.CoinMarketCapProvider;
import org.bot.providers.DataProvider;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

@Slf4j
public class PortfolioCommand implements Command {

    @Getter
    private final PortfolioLink portfolioLink;

    public PortfolioCommand(PortfolioLink portfolioLink) {
        this.portfolioLink = portfolioLink;
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        long chatId = update.getMessage().getChatId();
        String response = buildPortfolioResponse();
        sendText(chatId, response);
    }

    @Override
    public String getName() {
        return getPortfolioLink().getName();
    }

    @Override
    public String getDescription() {
        return "show portfolio " + getPortfolioLink().getName().substring(1);
    }

    private String buildPortfolioResponse() {
        try {
            DataProvider provider = CoinMarketCapProvider.getInstance();
            Portfolio portfolio = provider.getPortfolio(getPortfolioLink().getLink());
            portfolio.sort();
            return portfolio.toString();
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            return e.getMessage();
        }
    }
}
