package org.bot.commands;

import org.bot.commands.base.Command;
import org.bot.models.Portfolio;
import org.bot.utils.CoingeckoFacade;
import org.bot.utils.Utils;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MyPortfolioCommand implements Command {

    private static final String URL = Utils.getEnvVar("CG_URL_PRIVATE");

    public MyPortfolioCommand() {
        super();
    }

    @Override
    public void execute(Update update) {
        long chatId = update.getMessage().getChatId();
        try {
            String response = buildScamSalesResponse();
            sendText(chatId, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "/portfolio";
    }

    @Override
    public String getDescription() {
        return "show morre portfolio";
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
