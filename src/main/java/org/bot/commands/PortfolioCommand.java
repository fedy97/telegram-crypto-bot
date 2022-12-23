package org.bot.commands;

import org.bot.commands.base.Command;
import org.bot.models.Portfolio;
import org.bot.utils.CoingeckoFacade;
import org.bot.utils.Utils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class PortfolioCommand implements Command {

    private static final String URL = Utils.getEnvVar("CG_URL_PRIVATE");

    public PortfolioCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        long chatId = update.getMessage().getChatId();
        String response = buildScamSalesResponse();
        sendText(chatId, response);
    }

    @Override
    public String getName() {
        return "/portfolio";
    }

    @Override
    public String getDescription() {
        return "show owner portfolio";
    }

    private String buildScamSalesResponse() {
        CoingeckoFacade coingeckoFacade = CoingeckoFacade.getInstance();
        Portfolio portfolio = coingeckoFacade.getCoingeckoPortfolio(URL);
        return portfolio.toString();
    }

    @Override
    public boolean isValidated() {
        return URL.contains("coingecko.com/it/portfolios/public/");
    }

}
