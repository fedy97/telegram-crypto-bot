package org.bot.commands;

import org.bot.models.Portfolio;
import org.bot.utils.CoingeckoFacade;
import org.bot.utils.Utils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;


public class MyPortfolioCommand extends Command {

    public MyPortfolioCommand() {
        super();
        setName("/portfolio");
        setDescription("show morre portfolio");
    }

    @Override
    public void execute(Update update) {
        long chatId = update.getMessage().getChatId();
        try {
            String response = buildScamSalesResponse();
            sendText(chatId, response);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String buildScamSalesResponse() throws IOException {
        CoingeckoFacade coingeckoFacade = CoingeckoFacade.getInstance();
        Portfolio portfolio = coingeckoFacade.getCoingeckoPortfolio(Utils.getEnvVar("CG_URL_PRIVATE"));
        return portfolio.toString();
    }

}
