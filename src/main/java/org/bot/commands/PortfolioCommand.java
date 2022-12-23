package org.bot.commands;

import lombok.Getter;
import org.bot.commands.base.Command;
import org.bot.models.Portfolio;
import org.bot.models.PortfolioLink;
import org.bot.utils.CoingeckoFacade;
import org.bot.utils.Validator;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        CoingeckoFacade coingeckoFacade = CoingeckoFacade.getInstance();
        Portfolio portfolio = coingeckoFacade.getCoingeckoPortfolio(getPortfolioLink().getLink());
        return portfolio.toString();
    }

    @Override
    public boolean isValidated() {
        return Validator.validCoingeckoLink(getPortfolioLink().getLink());
    }

}
